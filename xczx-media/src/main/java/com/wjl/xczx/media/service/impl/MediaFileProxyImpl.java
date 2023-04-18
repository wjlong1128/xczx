//package com.wjl.xczx.media.service.impl;
//
//import com.wjl.xczx.common.exception.Assert;
//import com.wjl.xczx.media.exception.MediaException;
//import com.wjl.xczx.media.exception.state.MediaStateEnum;
//import com.wjl.xczx.media.mapper.MediaFileMapper;
//import com.wjl.xczx.media.model.entity.MediaFile;
//import com.wjl.xczx.media.model.entity.MediaProcess;
//import com.wjl.xczx.media.model.vo.MediaFileVO;
//import com.wjl.xczx.media.service.MediaFileProxy;
//import com.wjl.xczx.media.service.MediaProcessService;
//import com.wjl.xczx.media.service.MinioService;
//import com.wjl.xczx.media.utils.MimeTypeUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.concurrent.ThreadPoolExecutor;
//
///*
// * @author Wang Jianlong
// * @version 1.0.0
// * @date 2023/4/16
// * @description
// */
//@RequiredArgsConstructor
//@Service
//@Slf4j
//public class MediaFileProxyImpl implements MediaFileProxy {
//
//
//    private final MediaFileMapper mapper;
//
//    private final MinioService minioService;
//
//    private final ThreadPoolExecutor mediaThreadPoolExecutor;
//
//
//    @Value("#{'${media.ffmpeg.filetypes}'.split(',')}")
//    private List<String> types;
//
//    @Transactional
//    public MediaFileVO saveMediaFileInfo(Long companyId, MediaFile mediaFile,String type) {
//        mediaFile.setAuditStatus("02003");
//        mediaFile.setStatus("1");
//        mediaFile.setCompanyId(companyId);
//        mediaFile.setFileType(type);
//        int save = mapper.insert(mediaFile);
//        Assert.change(save, () -> new MediaException(MediaStateEnum.FILE_SAVE_ERROR));
//        MediaFileVO mediaFileVO = new MediaFileVO();
//        BeanUtils.copyProperties(mediaFile, mediaFileVO);
//        return mediaFileVO;
//    }
//
//
//
//    @Transactional
//    public void saveMediaVideoAndClearChunk(Integer chunkTotal, String chunkFilePath, String bucket, MediaFile mediaFile) {
//        mapper.insert(mediaFile);
//        // 清除分块文件
//        mediaThreadPoolExecutor.execute(()->{
//            log.debug("清除分块文件 bucket: {}, chunkFilePath: {}, chunkTotal:{}", bucket, chunkFilePath, chunkTotal);
//            minioService.clearChunk(bucket, chunkFilePath, chunkTotal);
//        });
//
//        String filename = mediaFile.getFilename();
//        String mimeType = MimeTypeUtils.getMimeType(filename);
//        // 插入待处理表
//        if (!types.contains(mimeType)) {
//            return;
//        }
//        // 根据不同文件名执行不同逻辑..... 假定为 video/x-msvideo
//        if (mimeType.equals("video/x-msvideo")){
//            MediaProcess process = new MediaProcess();
//            BeanUtils.copyProperties(mediaFile,process);
//            process.setStatus(MediaProcess.NOT_PROCESSED);
//            process.setCreateDate(LocalDateTime.now());
//            process.setFailCount(0);
//            processService.save(process);
//        }
//    }
//
//}
