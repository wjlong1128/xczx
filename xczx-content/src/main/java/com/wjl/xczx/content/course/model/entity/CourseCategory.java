package com.wjl.xczx.content.course.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description 课程分类表
 */
@Data
@TableName("course_category")
public class CourseCategory {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类标签默认和名称一样
     */
    private String label;

    /**
     * 父结点id（第一级的父节点是0，自关联字段id）
     */
    private String parentid;

    /**
     * 是否显示
     */
    private Integer isShow;

    /**
     * 排序字段
     */
    private Integer orderby;

    /**
     * 是否叶子
     */
    private Integer isLeaf;
}
