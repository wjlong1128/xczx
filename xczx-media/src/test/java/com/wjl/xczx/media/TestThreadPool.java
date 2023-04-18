package com.wjl.xczx.media;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/17
 * @description
 */
@SpringBootTest
public class TestThreadPool {


    @Autowired
    private ThreadPoolExecutor mediaThreadPoolExecutor;

    @Test
    void testPool() throws InterruptedException {
        mediaThreadPoolExecutor.submit(()-> {
            System.out.println("a");
        });
        TimeUnit.SECONDS.sleep(1L);
        System.out.println(mediaThreadPoolExecutor.getPoolSize());
        System.out.println(mediaThreadPoolExecutor.getMaximumPoolSize());
    }
}
