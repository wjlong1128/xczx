//package com.wjl.xczx.media.service;
//
//import com.wjl.xczx.media.model.entity.MediaFile;
//import com.wjl.xczx.media.model.vo.MediaFileVO;
//
///*
// * @author Wang Jianlong
// * @version 1.0.0
// * @date 2023/4/16
// * @description
// */
//public interface MediaFileProxy {
//    /**
//     *  防止事务失效的子集提升方法
//     * @param companyId
//     * @return
//     */
//
//    MediaFileVO saveMediaFileInfo(Long companyId, MediaFile mediaFile, String type);
//
//    /**
//     *  分片上传的文件入库，并且清理分片，记录待处理表
//     * @param chunkTotal
//     * @param chunkFilePath
//     * @param bucket
//     * @param mediaFile
//     */
//    void saveMediaVideoAndClearChunk(Integer chunkTotal, String chunkFilePath, String bucket, MediaFile mediaFile);
//}
