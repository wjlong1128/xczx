package com.wjl.xczx.content.course.service;

import com.wjl.xczx.content.course.model.entity.CourseBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/14
 * @description
 */
@SpringBootTest
public class CourseServiceTest {

    @Autowired
    private CourseBaseService courseBaseService;

    @Test
    void selectAll() {
        List<CourseBase> courseBaseList = courseBaseService.list();
        Assert.notEmpty(courseBaseList,"not null");
        courseBaseList.forEach(System.out::println);
    }


}
