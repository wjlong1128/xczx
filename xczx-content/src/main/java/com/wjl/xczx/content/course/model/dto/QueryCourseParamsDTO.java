package com.wjl.xczx.content.course.model.dto;

import lombok.Data;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @description
 * @date 2023/4/14
 */
@Data
public class QueryCourseParamsDTO {
    //审核状态
    private String auditStatus;
    //课程名称
    private String courseName;
    //发布状态
    private String publishStatus;

}
