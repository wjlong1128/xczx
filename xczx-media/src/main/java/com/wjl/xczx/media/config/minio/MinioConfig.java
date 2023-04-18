package com.wjl.xczx.media.config.minio;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/16
 * @description
 */

@Configuration
public class MinioConfig {

    @Bean
    public MinioClient minioClient(MinIoProperties properties) {
        return MinioClient
                .builder()
                .endpoint(properties.getUrl())
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .build();
    }

}
