package com.wjl.xczx.content.course.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/18
 * @description
 */
@Data
public class CoursePreviewVO implements Serializable {

    /**
     *  课程基本信息
     */
    private CourseInfoVO courseBase;

    /**
     *  课程计划信息
     */
    private List<TeachplanVO> teachplans;

    /**
     *  师资信息
     */
    private List<TeacherVO> teachers;


}
