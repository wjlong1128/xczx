package com.wjl.xczx.content.course.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
@Data
public class  AddCourseDTO implements Serializable {
    /**
     * 课程名称
     */
    @NotEmpty(message = "课程名称不能为空")
    private String name;
    /**
     * 适用人群
     */
    @NotEmpty(message = "适用人群不能为空")
    //@Size(message = "适用人群内容过少",min = 10)
    private String users;
    /**
     * 课程标签
     */
    @NotEmpty(message = "标签不能为空")
    private String tags;
    /**
     * 大分类
     */
    @NotEmpty(message = "课程分类不能为空")
    private String mt;
    /**
     * 小分类
     */
    @NotEmpty(message = "课程分类不能为空")
    private String st;
    /**
     * 课程等级
     */
    @NotEmpty(message = "课程等级不能为空")
    private String grade;
    /**
     * 教育模式(common普通，record 录播，live直播等）
     */
    @NotEmpty(message = "课程模式不能为空")
    private String teachmode;
    /**
     * 课程介绍
     */
    @Size(message = "课程描述内容过少",min = 10)
    private String description;
    /**
     * 课程图片
     */
    //@NotEmpty(message = "课程图片不能为空")
    private String pic;
    /**
     *  收费规则
     */
    @NotEmpty(message = "收费规则不能为空")
    private String charge;
    /**
     *  原价
     */
    private BigDecimal price;
    /**
     *  现价
     */
    private BigDecimal originalPrice;
    /**
     *  qq
     */
    private String qq;

    /**
     *  微信
     */
    private String wechat;

    /**
     *  电话
     */
    private String phone;

    /**
     * 有效期
     */
    private Integer validDays;

}
