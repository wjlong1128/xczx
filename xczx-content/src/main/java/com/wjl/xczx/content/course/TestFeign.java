package com.wjl.xczx.content.course;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/19
 * @description
 */
@FeignClient("test")
public interface TestFeign {
    @PostMapping(value ="testfile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String testFile(@RequestPart("file") MultipartFile file);
}
