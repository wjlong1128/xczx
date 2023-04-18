package com.wjl.xczx.content.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.content.course.model.dto.TeacherDTO;
import com.wjl.xczx.content.course.model.entity.CourseTeacher;
import com.wjl.xczx.content.course.model.vo.TeacherVO;

import java.util.List;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
public interface CourseTeacherService extends IService<CourseTeacher> {
    /**
     * 根据课程id查询教师集合
     * @param courseId
     * @return
     */
    Result<List<TeacherVO>> listByCourseId(Long courseId);

    /**
     *  保存或者修改
     * @param teacherDTO
     * @return
     */
    Result<TeacherVO> saveTeacher(TeacherDTO teacherDTO);

    /**
     *  删除课程中的教师
     * @param courseId
     * @param teacherId
     * @return
     */
    Result<Object> deleteByCourseId(Long courseId, Long teacherId);
}
