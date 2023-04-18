package com.wjl.xczx.media.config.basic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/17
 * @description
 */
@Configuration
public class ThreadPoolConfig {

    @Value("${media.thread-pool.core:10}")
    private int core;
    @Value("${media.thread-pool.max:12}")
    private int max;
    @Value("${media.thread-pool.keep-alive-time:30}")
    private int keepAliveTime;

    @Value("${media.thread-pool.queue-size:1024}")
    private int queueSize;


    // 默认策略 调用方执行
    @Bean("mediaThreadPoolExecutor")
    public ThreadPoolExecutor mediaThreadPoolExecutor(){
        return new ThreadPoolExecutor(core, max, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue<>(queueSize), Executors.defaultThreadFactory(),new ThreadPoolExecutor.CallerRunsPolicy());
    }

}
