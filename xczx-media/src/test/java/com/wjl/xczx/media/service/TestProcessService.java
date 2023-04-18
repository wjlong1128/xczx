package com.wjl.xczx.media.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/17
 * @description
 */
@SpringBootTest
public class TestProcessService {

    @Autowired
    MediaProcessHistoryService historyService;

    @Autowired
    MediaProcessService processService;

    @Test
    void test() {
        System.out.println(historyService.list());
        System.out.println(processService.list());
    }
}
