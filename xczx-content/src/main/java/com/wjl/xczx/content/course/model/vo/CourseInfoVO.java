package com.wjl.xczx.content.course.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
@Data
public class CourseInfoVO {
    /**
     * 主键
     */

    private Long id;
    /**
     * 机构ID
     */

    private Long companyId;
    /**
     * 机构名称
     */

    private String companyName;
    /**
     * 课程名称
     */

    private String name;
    /**
     * 适用人群
     */

    private String users;
    /**
     * 课程标签
     */

    private String tags;
    /**
     * 大分类
     */

    private String mt;
    /**
     * 小分类
     */

    private String st;
    /**
     * 课程等级
     */

    private String grade;
    /**
     * 教育模式(common普通，record 录播，live直播等）
     */

    private String teachmode;
    /**
     * 课程介绍
     */

    private String description;
    /**
     * 课程图片
     */

    private String pic;
    /**
     * 创建时间
     */


    private LocalDateTime createDate;
    /**
     * 修改时间
     */

    private LocalDateTime changeDate;
    /**
     * 创建人
     */
    private String createPeople;
    /**
     * 更新人
     */

    private String changePeople;
    /**
     * 审核状态
     */

    private String auditStatus;
    /**
     * 课程发布状态 未发布  已发布 下线
     */

    private String status;

    /**
     * 创建人
     */

    /**
     * 收费规则，对应数据字典
     */
    private String charge;

    /**
     * 价格
     */
    private Float price;


    /**
     * 原价
     */
    private Float originalPrice;

    /**
     * 咨询qq
     */
    private String qq;

    /**
     * 微信
     */
    private String wechat;

    /**
     * 电话
     */
    private String phone;

    /**
     * 有效期天数
     */
    private Integer validDays;

    /**
     * 大分类名称
     */
    private String mtName;

    /**
     * 小分类名称
     */
    private String stName;


}
