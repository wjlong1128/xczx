package com.wjl.xczx.content.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjl.xczx.common.result.PageResult;
import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.common.vo.PageParams;
import com.wjl.xczx.content.course.model.dto.AddCourseDTO;
import com.wjl.xczx.content.course.model.dto.EditCourseDTO;
import com.wjl.xczx.content.course.model.dto.QueryCourseParamsDTO;
import com.wjl.xczx.content.course.model.entity.CourseBase;
import com.wjl.xczx.content.course.model.vo.CourseInfoVO;
import com.wjl.xczx.content.course.model.vo.CourseVO;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @description
 * @date 2023/4/14
 */
public interface CourseBaseService  extends IService<CourseBase> {

    /**
     *  根据分页等条件查询课程集合
     * @param params
     * @param queryCourseParamsDTO
     * @return
     */
    Result<PageResult<CourseVO>> queryCourseWithCondition(
            Long companyId,
            PageParams params, QueryCourseParamsDTO queryCourseParamsDTO);

    /**
     *  新增课程
     * @param addCourseDTO
     * @return 返回课程信息
     */
    Result<CourseInfoVO> addCourse(Long companyId,AddCourseDTO addCourseDTO);

    /**
     *  查询课程信息
     * @param courseId
     * @return
     */
    Result<CourseInfoVO> getCourseById(Long companyId,Long courseId);

    /**
     *  修改课程信息
     * @param editCourseDTO
     * @return
     */
    Result<CourseInfoVO> editCourse(Long companyId,EditCourseDTO editCourseDTO);

    /**
     *  删除课程
     * @param companyId
     * @param courseId
     * @return
     */
    Result<Object> deleteCourse(Long companyId,Long courseId);


    /**
     *  针对于用户端的查看基本信息
     * @param courseId
     * @return
     */
    CourseInfoVO getCourseById(Long courseId);


}
