package com.wjl.xczx.content.course.controller;

import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.content.course.model.vo.CourseCategoryTreeVO;
import com.wjl.xczx.content.course.service.CourseCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("course-category")
public class CourseCategoryController {

    private final CourseCategoryService courseCategoryService;

    @GetMapping("tree-nodes")
    public Result<List<CourseCategoryTreeVO>> treeNodes(){
        return courseCategoryService.treeNodes();
    }

}
