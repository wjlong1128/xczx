package com.wjl.xczx.media.service;

import com.wjl.xczx.media.model.dto.FileInfoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/16
 * @description
 */
@SpringBootTest
public class MinioServiceTest {

    @Autowired
    private MinioService minIOUploadService;

    @Test
    void testIsExist() {
        FileInfoDTO infoDTO = new FileInfoDTO();
        infoDTO.setBucket("xczx-media").setFilePath("test/target1.mp4");
        // System.out.println(minIOUploadService.isExist(infoDTO));
    }
}
