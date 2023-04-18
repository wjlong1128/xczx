package com.wjl.xczx.media.service;

import com.wjl.xczx.media.exception.MediaException;

import java.io.File;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/16
 * @description 上传文件的接口
 */
public interface MinioService {
    /**
     * 原始文件上传
     */
    void uploadFile(String bucket, String fileType, String localPath, String objectName) throws MediaException;

    /**
     * 上传单个文件
     *
     * @param bucket
     * @param contentType
     * @param objectName  对象存储中名称
     * @param tempFile    要传递的临时文件
     * @return
     * @throws MediaException
     */
    public void uploadFile(String bucket, String contentType, String objectName, File tempFile) throws MediaException;

    /**
     * 查询文件是否存在
     *
     * @param bucket 桶名
     * @return
     */
    boolean isExist(String bucket, String filePath) throws MediaException;


    /**
     * 合并文件
     *
     * @param bucket
     * @param chunkFilePath
     * @param chunkTotal
     * @param objectName
     * @throws MediaException
     */
    void mergeChunksFile(String bucket, String chunkFilePath, Integer chunkTotal, String objectName) throws MediaException;

    /**
     * 下载文件
     *
     * @param bucket
     * @param objectName
     * @return
     */
    File downloadFile(String bucket, String objectName) throws MediaException;

    /**
     * 清理分块文件
     *
     * @param bucket
     * @param chunkPath
     * @param chunkTotal
     */
    void clearChunk(String bucket, String chunkPath, int chunkTotal) throws MediaException;
}
