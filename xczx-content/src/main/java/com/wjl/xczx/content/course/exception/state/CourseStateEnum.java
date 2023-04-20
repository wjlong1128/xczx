package com.wjl.xczx.content.course.exception.state;

import com.wjl.xczx.common.State;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
public enum CourseStateEnum implements State {
    ADD_ERROR(40001, "课程添加失败"),
    CHARGE_ERROR(40002, "收费规则错误"),
    PRICE_ERROR(40003, "价格错误"),
    QUERY_ERROR(40004, "课程查询失败"),
    EDIT_ERROR(40005, "修改课程失败"),
    UNAUTHORIZED_ERROR(40006, "只能修改属于自己的课程信息"),
    TECH_ERROR(40007, "课程计划添加失败"),
    TECH_UPDATE_ERROR(40008, "课程计划修改失败"),
    TECH_DELETE_ERROR(40009, "删除课程计划失败"),
    MOVE_FAIL(40010, "移动失败"),
    ADD_TEACHER_ERROR(40011, "添加教师失败"),
    UPDATE_TEACHER_ERROR(40012, "修改教师失败"),
    DEL_TEACHER_ERROR(40013, "删除课程教师失败"),
    NOT_DEL_COURSE(40013, "不能删除不存在的课程"),
    DEL_COURSE_STATE_ERROR(40013, "只能删除未提交的课程"),
    DEL_COURSE_ERROR(40014, "删除课程失败"),
    DEL_MARKET_ERROR(40015, "删除营销信息失败"),
    DEL_TEACH_BATCH_BY_COURSE_ERROR(40015, "根据课程批量删除课程计划失败"), 
    DEL_BATCH_BY_TEACH_ERROR(40016,"根据课程计划删除媒资信息失败" ),
    TECH_QUERY_NOT_NULL_ERROR(40017, "课程计划不能为空"),
    REPEAT_COMMIT_ERROR(40017,"课程已经提交,请等待"),
    PIC_NOT_NULL(40017,"请上传课程图片"),
    COURSE_TEAC_NOT_NULL(40017,"请上传课程计划"),
    UNDER_REVIEW(40017,"课程审核中" ),
    COURSE_NOT_AUDIT(40018,"课程没有审核记录" ),
    UPLOAD_HTML_ERROR(40018,"上传静态界面异常");

    CourseStateEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
