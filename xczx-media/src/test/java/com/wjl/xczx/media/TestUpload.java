package com.wjl.xczx.media;

import com.wjl.xczx.media.config.minio.MinIoProperties;
import com.wjl.xczx.media.utils.MediaFileUtils;
import io.minio.*;
import io.minio.errors.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/16
 * @description
 */
@SpringBootTest
public class TestUpload {

    @Autowired
    private MinioClient client;

    @Autowired
    private MinIoProperties properties;

    @Value("${media.ffmpeg}")
    private String ffmpeg;

    @Test
    void uploadFile() throws Exception {
        UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder().bucket(properties.getBucket().getFiles()).object(MediaFileUtils.getDatePath() + "test/1.jpg").filename("C:\\Users\\wangj\\Desktop\\p.jpg").build();
        ObjectWriteResponse response = client.uploadObject(uploadObjectArgs);
        System.out.println(response);
    }

    @Test
    void testUtils() {
        String suffix = MediaFileUtils.suffix("wjl.jpg");
        System.out.println(suffix);
    }

    @Test
    void testProperties() {
        System.out.println(properties);
    }

    @Test
    void testGet()  {
        GetObjectResponse object = null;
        try {
            String name = properties.getBucket().getFiles();
            object = client.getObject(GetObjectArgs.builder().bucket("blog").object("test/target.mpq").build());
            System.out.println(object);
        } catch (ErrorResponseException e) {
            System.out.println(e.response());
        } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        }

        System.out.println(object);
    }

    @Test
    void path() {
        System.out.println(ffmpeg);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadLocal<String> local = new InheritableThreadLocal<>();
        local.set("a");

        new Thread(()->{
            System.out.println(local.get());
        }).start();

        TimeUnit.SECONDS.sleep(1L);

    }
}
