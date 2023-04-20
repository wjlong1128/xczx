package com.wjl.xczx.feign.config;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/20
 * @description
 */

import com.wjl.xczx.feign.client.demote.MediaServiceClientFallbackFactory;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfig {

    @Bean
    public MediaServiceClientFallbackFactory mediaServiceClientFallbackFactory(){
        return new MediaServiceClientFallbackFactory();
    }

}
