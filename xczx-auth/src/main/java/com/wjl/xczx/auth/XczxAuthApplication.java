package com.wjl.xczx.auth;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/20
 * @description
 */
@SpringBootApplication
public class XczxAuthApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(XczxAuthApplication.class).web(WebApplicationType.SERVLET).run(args);
    }

}
