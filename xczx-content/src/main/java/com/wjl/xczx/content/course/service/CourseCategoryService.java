package com.wjl.xczx.content.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.content.course.model.entity.CourseCategory;
import com.wjl.xczx.content.course.model.vo.CourseCategoryTreeVO;

import java.util.List;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
public interface CourseCategoryService extends IService<CourseCategory> {

    /**
     *  获取分类节点
     * @return 根节点下所有的子节点
     */
    Result<List<CourseCategoryTreeVO>> treeNodes();

    String getCategoryNameById(String st);
}
