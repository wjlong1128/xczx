package com.wjl.xczx.feign.client.demote;

import com.wjl.xczx.feign.client.MediaServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.web.multipart.MultipartFile;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/20
 * @description
 */
@Slf4j
public class MediaServiceClientFallbackFactory implements FallbackFactory<MediaServiceClient> {
    @Override
    public MediaServiceClient create(Throwable cause) {
        return new MediaServiceClient() {
            @Override
            public String uploadFile(MultipartFile file, String objectName, String tags) {
                log.error("远程上传文件失败, objectName:{}, message:{}",objectName,cause.getMessage(),cause);
                return null;
            }
        };
    }
}
