package com.wjl.xczx.content.course.feign;

import com.wjl.xczx.feign.config.MultipartSupportConfig;
import com.wjl.xczx.feign.client.MediaServiceClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/20
 * @description
 */
@SpringBootTest
public class TestMediaServiceClient {

    @Autowired
    MediaServiceClient mediaServiceClient;

    @Test
    void testFileUpload() throws IOException {
        MultipartFile file = MultipartSupportConfig.getMultipartFile("file", new ClassPathResource("dict.json").getFile());
        String result = mediaServiceClient.uploadFile(file, "xczx/dict.json", "json");
        System.out.println(result);
    }
}
