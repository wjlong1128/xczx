package com.wjl.xczx.content.course.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
@Data
public class AddTeachplanDTO implements Serializable {

    private Long id;
    @NotNull(message = "课程id不能为空")
    private Long courseId;
    //@NotNull(message = "课程的父级id不能为空")
    private Long parentid;
    @NotNull(message = "等级不能为空")
    private Integer grade;

    @NotEmpty(message = "章节名不能为空")
    private String pname;

    /**
     * 课程类型:1视频、2文档
     */
    // @NotEmpty(message = "课程类型不能为空")
    private String mediaType;


    /**
     * 课程发布标识
     */
    private Long coursePubId;


    /**
     * 是否支持试学或预览（试看）
     */
    private String isPreview;

}
