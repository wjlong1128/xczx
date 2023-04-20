package com.wjl.demo.api;

import com.wjl.xczx.common.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/18
 * @description
 */
@RestController
public class TestController {

    @GetMapping("test")
    public Result test(){
        return Result.success("我是");
    }

    @PostMapping("testfile")
    public String testFile(@RequestPart("file") MultipartFile file){
        return file.getContentType();
    }

}
