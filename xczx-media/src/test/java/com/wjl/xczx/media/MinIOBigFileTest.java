package com.wjl.xczx.media;

import com.alibaba.nacos.common.http.param.MediaType;
import com.wjl.xczx.media.config.minio.MinIoProperties;
import io.minio.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/16
 * @description
 */
@SpringBootTest
public class MinIOBigFileTest {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinIoProperties properties;

    @Test
    void testChunkUpload() throws Exception {
        File sourceFile = new File("D:\\download\\douyin.mp4");
        File chunkFolder = new File("C:\\Users\\wangj\\Desktop\\chunk\\");
        int size = 1024 * 1024 * 10;

        int num = (int) Math.ceil((double) sourceFile.length() / size);

        RandomAccessFile r = new RandomAccessFile(sourceFile, "r");
        for (int i = 0; i < num; i++) {
            File chunkFile = new File(chunkFolder.getAbsolutePath() + "/" + i);
            RandomAccessFile rw = new RandomAccessFile(chunkFile, "rw");
            int len = -1;
            byte[] buf = new byte[1024];
            while ((len = r.read(buf)) != -1) {
                rw.write(buf, 0, len);
                if (chunkFile.length() >= size) {
                    break;
                }
            }
            rw.close();

            minioClient.uploadObject(
                    UploadObjectArgs
                            .builder()
                            .bucket(properties.getBucket().getVideofiles())
                            .contentType(MediaType.APPLICATION_OCTET_STREAM)
                            .filename(chunkFile.getAbsolutePath())
                            .object("test" + "/chunk/" + chunkFile.getName())
                            .build()
            );
        }

        r.close();
    }

    @Test
    void testMinioMerge() throws Exception {
        File sourceFile = new File("D:\\download\\douyin.mp4");
        // File chunkFolder = new File("C:\\Users\\wangj\\Desktop\\chunk\\");
        int size = 1024 * 1024 * 10;
        int num = (int) Math.ceil((double) sourceFile.length() / size);

        List<ComposeSource> sources = new ArrayList<>();
        String bucket = properties.getBucket().getVideofiles();
        for (int i = 0; i < num; i++) {

            ComposeSource.Builder builder = ComposeSource.builder().bucket(bucket)
                    .object("test/chunk/" + i);
            sources.add(builder.build());
        }
        ComposeObjectArgs args = ComposeObjectArgs
                .builder()
                .bucket(bucket)
                .sources(sources)
                .object("test/target.mp4")
                .build();

        ObjectWriteResponse response = minioClient.composeObject(args);
        System.out.println(response.headers());
        System.out.println(response.etag());
        System.out.println(response.object());
        System.out.println(response.region());
    }
}
