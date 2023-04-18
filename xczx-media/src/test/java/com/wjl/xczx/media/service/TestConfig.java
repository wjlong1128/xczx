package com.wjl.xczx.media.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;

import java.util.List;
import java.util.Optional;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/17
 * @description
 */
@SpringBootTest
public class TestConfig {

    @Value("#{'${media.ffmpeg.filetypes}'.split(',')}")
    private List<String> types;

    @Test
    void test() {
        System.out.println(types);
    }

    @Test
    void mime() {
        Optional<MediaType> mediaType = MediaTypeFactory.getMediaType("dfsa.avi");
        System.out.println(mediaType.get().toString());
    }
}
