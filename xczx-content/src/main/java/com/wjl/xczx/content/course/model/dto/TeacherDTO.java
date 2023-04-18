package com.wjl.xczx.content.course.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
@Data
public class TeacherDTO implements Serializable {

    private Long id;

    @NotNull(message = "课程id不能为空")
    private Long courseId;

    @NotEmpty(message = "教师名称不能为空")
    private String teacherName;

    @NotEmpty(message = "教师职位不能为空")

    private String position;
    @NotEmpty(message = "教师简介不能为空")
    private String introduction;

    private LocalDateTime createDate;

    //"courseId": 75,
    //        "teacherName": "王老师",
    //        "position": "教师职位",
    //        "introduction": "教师简介"

}
