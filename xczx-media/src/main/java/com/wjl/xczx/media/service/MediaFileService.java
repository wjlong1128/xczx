package com.wjl.xczx.media.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjl.xczx.common.result.PageResult;
import com.wjl.xczx.common.result.RestResponse;
import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.common.vo.PageParams;
import com.wjl.xczx.media.model.dto.QueryMediaParamsDTO;
import com.wjl.xczx.media.model.entity.MediaFile;
import com.wjl.xczx.media.model.vo.MediaFileVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/16
 * @description
 */
public interface MediaFileService extends IService<MediaFile> {

    /**
     *  根据机构id 分页条件查询接口
     * @param companyId
     * @param pageParams
     * @param queryMediaParamsDTO
     * @return
     */
    Result<PageResult<MediaFileVO>> queryMediaFiles(Long companyId, PageParams pageParams, QueryMediaParamsDTO queryMediaParamsDTO);

    /**
     * 上传课程图片
     *
     * @param file
     * @param companyId
     * @return
     */
    Result<MediaFileVO> uploadFile(Long companyId, MultipartFile file,String objectName,String tags);


    /**
     *  根据分块序号和md查看分块是否存在
     * @param fileMd5
     * @param chunk
     * @return
     */
    Result<Boolean> checkChunkFile(String fileMd5, Integer chunk);

    /**
     * 根据md查看文件是否存在 (秒传)
     * @param fileMd5
     * @return
     */
    Result<Boolean> checkFile(String fileMd5);

    /**
     *  上传分片
     * @param file
     * @param fileMd5
     * @param chunk
     * @return
     * @throws IOException
     */
    Result<Boolean> uploadChunk(MultipartFile file, String fileMd5, Integer chunk) throws IOException;

    /**
     *  柯炳视频
     * @param companyId
     * @param fileName
     * @param chunkTotal
     * @return
     */
    Result mergeChunks(Long companyId, String fileMd5,String fileName,  Integer chunkTotal);

    /**
     *  分片上传的文件入库，并且清理分片，记录待处理表
     * @param chunkTotal
     * @param chunkFilePath
     * @param bucket
     * @param mediaFile
     */
    void saveMediaVideoAndClearChunk(Integer chunkTotal, String chunkFilePath, String bucket, MediaFile mediaFile);

    /**
     *  防止事务失效的子集提升方法
     * @param companyId
     * @return
     */
    MediaFileVO saveMediaFileInfo(Long companyId, MediaFile mediaFile, String type);

    Result deleteFileById(Long companyId, String fileId);

    RestResponse<String> getFileUrl(String mediaId);
}
