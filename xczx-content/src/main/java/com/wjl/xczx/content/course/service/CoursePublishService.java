package com.wjl.xczx.content.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjl.xczx.content.course.exception.CourseException;
import com.wjl.xczx.content.course.model.entity.CoursePublish;
import com.wjl.xczx.content.course.model.vo.CoursePreviewVO;

import java.io.File;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/18
 * @description
 */
public interface CoursePublishService extends IService<CoursePublish> {

    /**
     *  页面的基本信息
     * @param courseId
     * @return
     */
    CoursePreviewVO coursePreview(Long courseId);

    /**
     *  提交审核
     * @param companyId
     * @param courseId
     */
    void commitAudit(Long  companyId, Long courseId);

    /**
     *  课程发布。将要发布的课程从预发布表添加到发布表
     * @param companyId
     * @param courseId
     */
    void publish(Long companyId, Long courseId);

    /**
     *  生成静态化页面文件
     * @param courseId
     * @return
     */
    File generateCourseHtml(Long courseId);


    void uploadCourseHtml(Long courseId,File htmlFile) throws CourseException;
}
