package com.wjl.xczx.media.service.impl;

import com.wjl.xczx.media.config.minio.MinIoProperties;
import com.wjl.xczx.media.exception.MediaException;
import com.wjl.xczx.media.exception.state.MediaStateEnum;
import com.wjl.xczx.media.service.MinioService;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/16
 * @description
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;

    private final MinIoProperties properties;

    @Override
    public void uploadFile(String bucket, String fileType, String localPath, String objectName) throws MediaException {
        try {
            UploadObjectArgs args = UploadObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .contentType(fileType)
                    .filename(localPath)
                    .build();
            minioClient.uploadObject(args);
            log.debug("文件上传成功 bucket:{}, objectName:{}, localPath: {}", bucket, objectName, localPath);
        } catch (Exception e) {
            throw new MediaException(e, MediaStateEnum.FILE_UPLOAD_ERROR);
        }
    }

    @Override
    public void uploadFile(String bucket, String contentType, String objectName, File tempFile) throws MediaException {
        this.uploadFile(bucket, contentType, tempFile.getAbsolutePath(), objectName);
    }

    @Override
    public boolean isExist(String bucket, String filePath) {
        try (GetObjectResponse response = minioClient
                .getObject(
                        GetObjectArgs
                                .builder()
                                .bucket(bucket)
                                .object(filePath)
                                .build()
                )) {
            boolean b = response != null;
            log.debug("{}/{} 存在？{}", bucket, filePath, b);
            return b;
        } catch (ErrorResponseException e) {
            if (e.response().code() == 404) {
                return false;
            } else {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new MediaException(e, MediaStateEnum.FILE_ACCESS_ERROR);
        }
    }


    @Override
    public void mergeChunksFile(String bucket, String chunkFilePath, Integer chunkTotal, String objectName) {
        try {
            List<ComposeSource> sourceList = Stream.iterate(0, i -> ++i).limit(chunkTotal)
                    .map(i -> ComposeSource.builder().bucket(bucket).object(chunkFilePath + Integer.toString(i)).build())
                    .collect(Collectors.toList());

            ComposeObjectArgs composeObjectArgs = ComposeObjectArgs
                    .builder()
                    .bucket(bucket)
                    .sources(sourceList)
                    .object(objectName)
                    .build();
            GenericResponse response = minioClient.composeObject(composeObjectArgs);
            log.debug("文件合并成功 bucket:{}, objectName:{}", bucket, objectName);
        } catch (Exception e) {
            throw new MediaException(e, MediaStateEnum.MERGE_FILE_ERROR);
        }
    }

    @Override
    public File downloadFile(String bucket, String objectName) throws MediaException {
        GetObjectResponse response = null;
        FileOutputStream in = null;
        try {
            response = minioClient.getObject(GetObjectArgs.builder().bucket(bucket).object(objectName).build());
            File file = File.createTempFile(bucket, "temp");
            in = new FileOutputStream(file);
            IOUtils.copy(response, in);
            return file;
        } catch (Exception e) {
            throw new MediaException(e, MediaStateEnum.FILE_NOT_FOUNT);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void clearChunk(String bucket, String chunkPath, int chunkTotal)  {
        List<DeleteObject> deleteObjects = Stream
                .iterate(0, i -> ++i)
                .limit(chunkTotal)
                .map(i -> new DeleteObject(chunkPath + i))
                .collect(Collectors.toList());
        RemoveObjectsArgs args = RemoveObjectsArgs
                .builder()
                .bucket(bucket)
                .objects(deleteObjects)
                .build();
        Iterable<Result<DeleteError>> results = minioClient.removeObjects(args);
        for (Result<DeleteError> result : results) {
            try {
                DeleteError error = result.get();
                log.debug("清除分块文件 {}, message:{}",error.code(),error.message());
            } catch (Exception e) {
               log.debug("清除分块文件失败 {}, e:{}",e.getMessage(),e);
            }
        }
    }


}
