package com.wjl.xczx.content.course.model.dto;

import lombok.Data;

import java.io.Serializable;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/18
 * @description 绑定媒资和课程计划dto
 */
@Data
public class BindTeachPlanMediaDTO implements Serializable {

    private String fileName;

    private String  mediaId;

    private Long teachplanId;

}
