package com.wjl.xczx.content;

import com.wjl.xczx.feign.config.DefaultFeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @description
 * @date 2023/4/14
 */

@EnableFeignClients(
        //clients = {MediaServiceClient.class},
        basePackages = {"com.wjl.xczx.feign"},
        defaultConfiguration = DefaultFeignConfig.class)
@SpringBootApplication
public class XczxContentApplication {
    public static void main(String[] args) {
        SpringApplication.run(XczxContentApplication.class,args);
    }
}
