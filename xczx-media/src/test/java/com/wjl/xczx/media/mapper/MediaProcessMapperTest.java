package com.wjl.xczx.media.mapper;

import com.wjl.xczx.media.model.entity.MediaProcess;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/17
 * @description
 */
@SpringBootTest
public class MediaProcessMapperTest {

    @Autowired
    private MediaProcessMapper mapper;

    @Test
    void task() {
        List<MediaProcess> list = mapper.getPendingTasks(0, 1, 1);
        System.out.println(list);
    }
}
