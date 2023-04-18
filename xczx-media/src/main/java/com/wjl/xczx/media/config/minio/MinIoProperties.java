package com.wjl.xczx.media.config.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/16
 * @description
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinIoProperties {


    private String accessKey;

    private String secretKey;

    private String url;

    private Bucket bucket;
    @Data
    //@ConfigurationProperties(prefix = "minio.bucket")
    //@Component
    public static class Bucket{
        private String files;
        private String videofiles;
    }
}
