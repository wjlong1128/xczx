package com.wjl.xczx.content.course.service;

import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.content.course.model.vo.CourseCategoryTreeVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
@SpringBootTest
public class CourseCategoryServiceTest {

    @Autowired
    private CourseCategoryService courseCategoryService;
    @Test
    void treeNodes() {
        Result<List<CourseCategoryTreeVO>> nodes = courseCategoryService.treeNodes();
        List<CourseCategoryTreeVO> list = nodes.getData();
        Assert.notEmpty(list,"not null");
        list.forEach(System.out::println);
    }
}
