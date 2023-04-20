package com.wjl.xczx.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjl.xczx.common.consts.XczxConstant;
import com.wjl.xczx.common.exception.Assert;
import com.wjl.xczx.common.result.PageResult;
import com.wjl.xczx.common.result.RestResponse;
import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.common.result.status.StateEnum;
import com.wjl.xczx.common.vo.PageParams;
import com.wjl.xczx.media.config.minio.MinIoProperties;
import com.wjl.xczx.media.exception.MediaException;
import com.wjl.xczx.media.exception.state.MediaStateEnum;
import com.wjl.xczx.media.mapper.MediaFileMapper;
import com.wjl.xczx.media.model.dto.QueryMediaParamsDTO;
import com.wjl.xczx.media.model.entity.MediaFile;
import com.wjl.xczx.media.model.entity.MediaProcess;
import com.wjl.xczx.media.model.entity.RemoveFile;
import com.wjl.xczx.media.model.vo.MediaFileVO;
import com.wjl.xczx.media.service.MediaFileService;
import com.wjl.xczx.media.service.MediaProcessService;
import com.wjl.xczx.media.service.MinioService;
import com.wjl.xczx.media.service.RemoveFileService;
import com.wjl.xczx.media.utils.MediaFileUtils;
import com.wjl.xczx.media.utils.MimeTypeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/16
 * @description
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MediaFileServiceImpl extends ServiceImpl<MediaFileMapper, MediaFile> implements MediaFileService {


    private final MinioService minioService;


    private final MinIoProperties properties;


    private final MediaFileMapper mapper;

    private final ThreadPoolExecutor mediaThreadPoolExecutor;

    private final RemoveFileService removeFileService;

    @Lazy
    @Autowired
    private MediaProcessService processService;
    @Lazy
    @Autowired
    private MediaFileService proxyMediaFileService; // 可以在自带方法中使用AopContext.currentProxy()

    @Value("#{'${media.ffmpeg.filetypes}'.split(',')}")
    private List<String> types;




    @Override
    public Result<PageResult<MediaFileVO>> queryMediaFiles(Long companyId, PageParams pageParams, QueryMediaParamsDTO queryMediaParamsDTO) {
        Assert.notNull(companyId, () -> new MediaException(StateEnum.NOT_AUTHENTICATION));
        LambdaQueryWrapper<MediaFile> wrapper = new LambdaQueryWrapper<>();
        // 组装查询条件
        if (queryMediaParamsDTO != null) {
            String fileType = queryMediaParamsDTO.getType();
            String auditStatus = queryMediaParamsDTO.getAuditStatus();
            String filename = queryMediaParamsDTO.getFilename();
            wrapper.eq(MediaFile::getCompanyId, companyId);
            wrapper.eq(StringUtils.hasText(fileType), MediaFile::getFileType, fileType);
            wrapper.eq(StringUtils.hasText(auditStatus), MediaFile::getAuditStatus, auditStatus);
            if (StringUtils.hasText(filename)) {
                wrapper.and(w -> {
                    w.like(MediaFile::getFilename, filename);
                });
            }
        }
        Page<MediaFile> page = page(new Page<>(pageParams.getPageNo(), pageParams.getPageSize()), wrapper);
        PageResult<MediaFileVO> data = new PageResult<>();
        data.setPage(page.getCurrent());
        data.setCounts(page.getTotal());
        data.setPageSize(page.getSize());
        List<MediaFileVO> items = page.getRecords().stream().map(item -> {
            MediaFileVO vo = new MediaFileVO();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).collect(Collectors.toList());
        data.setItems(items);
        return Result.success(data);
    }

    // @Transactional  // 这里的事务粒度太大 io流事件占用资源时间长 可能会数据库连接不够用
    @Override
    public Result<MediaFileVO> uploadFile(Long companyId, MultipartFile file,String objectName,String tag) {
        Assert.notNull(companyId, () -> new MediaException(MediaStateEnum.COMPANY_ID_NULL_ERROR));
        // 保存临时文件 计算md5
        String originalFilename = file.getOriginalFilename();
        File temp = null;
        try {
            temp = File.createTempFile(originalFilename, "temp");
            file.transferTo(temp); // 拷贝
        } catch (IOException e) {
            throw new MediaException(e, MediaStateEnum.CRT_TEMP_ERROR);
        }
        // 获取md5值
        String md5 = MediaFileUtils.fileMD5(temp);

        // 库中是否存在
        MediaFile mediaFile = getById(md5);

        if (mediaFile != null) {
            MediaFileVO mediaFileVO = new MediaFileVO();
            BeanUtils.copyProperties(mediaFile, mediaFileVO);
            return Result.success(mediaFileVO);
        }
        String bucket = properties.getBucket().getFiles();
        String contentType = file.getContentType();
        String suffix = MediaFileUtils.suffix(originalFilename);
        if ( objectName == null ){
             objectName = MediaFileUtils.getDatePathFileName(md5 + suffix);
        }
        // 不存在 上传
        minioService.uploadFile(bucket, contentType, objectName, temp);
        mediaFile = new MediaFile();
        mediaFile.setId(md5);
        mediaFile.setBucket(bucket);
        mediaFile.setCompanyId(companyId);
        mediaFile.setFilename(originalFilename);
        String type = MediaFile.IMG;
        if (tag == null ){
            mediaFile.setTags("课程图片");
        }else{
            mediaFile.setTags(tag);
            type = MediaFile.OTHER;
        }
        mediaFile.setFilePath(objectName);
        mediaFile.setFileId(md5);
        mediaFile.setUrl("/" + bucket + "/" + objectName);
        mediaFile.setFileSize(temp.length());

        // 防止事务失效
        MediaFileVO mediaFileVO = proxyMediaFileService.saveMediaFileInfo(companyId, mediaFile, type);
        return Result.success(mediaFileVO);
    }

    @Override
    public Result<Boolean> checkChunkFile(String fileMd5, Integer chunk) {
        // 根据当前文件名(md5) 拼接出分块信息
        String path = getMd5ChunksFloder(fileMd5);
        boolean isExist = minioService.isExist(properties.getBucket().getVideofiles(), path + chunk);
        return Result.success(isExist);
    }

    @NotNull
    private static String getMd5ChunksFloder(String fileMd5) {
        String f = "/";
        return new StringBuilder().append(fileMd5.substring(0, 1)).append(f).append(fileMd5.substring(1, 2)).append(f).append(fileMd5.substring(2, 3)).append(f).append("chunk/").toString();
    }

    /**
     * 先查询数据库是否存在 如果存在查询对象存储中是否存在
     * 都存在才是存在
     *
     * @param fileMd5
     * @return
     */
    @Override
    public Result<Boolean> checkFile(String fileMd5) {
        MediaFile file = getById(fileMd5);
        boolean isExist = file != null;
        if (isExist) {
            isExist = minioService.isExist(file.getBucket(), file.getFilePath());
            if (!isExist) {
                // io 桶不存在 删除表
                boolean remove = removeById(file);
                log.debug("清除无用表信息 id:{}, mediaFile:{}", fileMd5, file);
            }
        }
        return Result.success(isExist);
    }

    @Override
    public Result<Boolean> uploadChunk(MultipartFile file, String fileMd5, Integer chunk) throws IOException {
        String objectName = getMd5ChunksFloder(fileMd5) + chunk;
        File tempFile = File.createTempFile(fileMd5, chunk.toString());
        file.transferTo(tempFile);

        String bucket = properties.getBucket().getVideofiles();
        String absolutePath = tempFile.getAbsolutePath();
        minioService.uploadFile(bucket, file.getContentType(), absolutePath, objectName);
        return Result.success(true);
    }

    @Override
    public Result mergeChunks(Long companyId, String fileMd5, String fileName, Integer chunkTotal) {
        Assert.notNull(companyId, () -> new MediaException(MediaStateEnum.COMPANY_ID_NULL_ERROR));
        // 1. 文件路径
        String path = getMd5Floder(fileMd5);
        // 2. 分块文件路径
        String chunkFilePath = getMd5ChunksFloder(fileMd5);
        String bucket = properties.getBucket().getVideofiles();
        String suffix = MediaFileUtils.suffix(fileName);
        // 合并后文件的位置
        String objectName = getMd5Floder(fileMd5) + fileMd5 + suffix; // 文件夹 + 文件名 + 后缀
        minioService.mergeChunksFile(bucket, chunkFilePath, chunkTotal, objectName);
        // 校验是否一致
        File downloadFile = minioService.downloadFile(bucket, objectName);
        boolean same = MediaFileUtils.fileMD5(downloadFile).equals(fileMd5);
        log.debug("合并后的文件与上传的文件是相同的 {}", String.valueOf(same));
        MediaFile mediaFile = new MediaFile();
        mediaFile.setId(fileMd5);
        mediaFile.setFilename(fileName);
        mediaFile.setCompanyId(companyId);
        mediaFile.setFileType(MediaFile.VIDEO);
        mediaFile.setFilePath(objectName);
        mediaFile.setUrl("/" + bucket + "/" + objectName);
        mediaFile.setBucket(bucket);
        mediaFile.setFileId(fileMd5);
        mediaFile.setTags("视频文件");
        // [{\"code\":\"002001\",\"desc\":\"审核未通过\"},{\"code\":\"002002\",\"desc\":\"未审核\"},{\"code\":\"002003\",\"desc\":\"审核通过\"}]"
        mediaFile.setAuditStatus(XczxConstant.ObjectAuditStatus.NOT_AUDITED.getCode());
        proxyMediaFileService.saveMediaVideoAndClearChunk(chunkTotal, chunkFilePath, bucket, mediaFile);
        return Result.success();
    }


    private String getMd5Floder(String fileMd5) {
        String f = "/";
        return new StringBuilder().append(fileMd5.substring(0, 1)).append(f).append(fileMd5.substring(1, 2)).append(f).append(fileMd5.substring(2, 3)).append(f).toString();
    }

    @Transactional
    public MediaFileVO saveMediaFileInfo(Long companyId, MediaFile mediaFile, String type) {
        // [{\"code\":\"002001\",\"desc\":\"审核未通过\"},{\"code\":\"002002\",\"desc\":\"未审核\"},{\"code\":\"002003\",\"desc\":\"审核通过\"}]"
        mediaFile.setAuditStatus(XczxConstant.ObjectAuditStatus.NOT_AUDITED.getCode());
        mediaFile.setStatus("1");
        mediaFile.setCompanyId(companyId);
        mediaFile.setFileType(type);
        int save = mapper.insert(mediaFile);
        Assert.change(save, () -> new MediaException(MediaStateEnum.FILE_SAVE_ERROR));
        MediaFileVO mediaFileVO = new MediaFileVO();
        BeanUtils.copyProperties(mediaFile, mediaFileVO);
        return mediaFileVO;
    }

    @Transactional
    @Override
    public Result deleteFileById(Long companyId, String fileId) {
        Assert.notNull(companyId, () -> new MediaException(MediaStateEnum.COMPANY_ID_NULL_ERROR));
        LambdaQueryWrapper<MediaFile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MediaFile::getCompanyId,companyId).eq(MediaFile::getId,fileId);
        MediaFile mediaFile = getOne(wrapper);
        RemoveFile removeFile = new RemoveFile();
        BeanUtils.copyProperties(mediaFile,removeFile);
        removeFile.setRec("1");
        removeFileService.save(removeFile);
        remove(wrapper);
        return Result.success();
    }

    @Override
    public RestResponse<String> getFileUrl(String mediaId) {
        MediaFile file = getById(mediaId);
        Assert.notNull(file,()-> new MediaException(MediaStateEnum.FILE_NOT_FOUNT));
        return RestResponse.success(file.getUrl());
    }


    @Transactional
    public void saveMediaVideoAndClearChunk(Integer chunkTotal, String chunkFilePath, String bucket, MediaFile mediaFile) {
        mapper.insert(mediaFile);
        // 清除分块文件
        mediaThreadPoolExecutor.execute(() -> {
            log.debug("清除分块文件 bucket: {}, chunkFilePath: {}, chunkTotal:{}", bucket, chunkFilePath, chunkTotal);
            minioService.clearChunk(bucket, chunkFilePath, chunkTotal);
        });

        String filename = mediaFile.getFilename();
        String mimeType = MimeTypeUtils.getMimeType(filename);
        // 插入待处理表
        if (!types.contains(mimeType)) {
            return;
        }
        // 根据不同文件名执行不同逻辑..... 假定为 video/x-msvideo
        if (mimeType.equals("video/x-msvideo")) {
            MediaProcess process = new MediaProcess();
            BeanUtils.copyProperties(mediaFile, process);
            process.setStatus(MediaProcess.NOT_PROCESSED);
            process.setCreateDate(LocalDateTime.now());
            process.setFailCount(0);
            processService.save(process);
        }
    }


}
