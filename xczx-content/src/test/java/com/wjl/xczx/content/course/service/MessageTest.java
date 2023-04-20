package com.wjl.xczx.content.course.service;

import com.wjl.xczx.messagesdk.service.MqMessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/19
 * @description
 */
@SpringBootTest
public class MessageTest {

    @Autowired
    private MqMessageService mqMessageService;

    @Test
    void name() {
        //System.out.println(mqMessageService.getClass());
        System.out.println(mqMessageService.list());
    }
}
