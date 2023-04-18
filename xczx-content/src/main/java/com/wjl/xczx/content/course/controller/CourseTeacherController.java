package com.wjl.xczx.content.course.controller;

import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.content.course.model.dto.TeacherDTO;
import com.wjl.xczx.content.course.model.vo.TeacherVO;
import com.wjl.xczx.content.course.service.CourseTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
@RequestMapping("courseTeacher")
@RestController
@RequiredArgsConstructor
public class CourseTeacherController {

    private final CourseTeacherService courseTeacherService;

    @GetMapping("list/{courseId}")
    public Result<List<TeacherVO>> list(@PathVariable("courseId") Long courseId){
        return courseTeacherService.listByCourseId(courseId);
    }

    @PostMapping
    public Result<TeacherVO> save(@RequestBody @Validated TeacherDTO teacherDTO){
        return courseTeacherService.saveTeacher(teacherDTO);
    }

    @DeleteMapping("course/{courseId}/{teacherId}")
    public Result<Object> deleteByCourseId(@PathVariable("courseId") Long courseId,@PathVariable("teacherId") Long teacherId){
        return courseTeacherService.deleteByCourseId(courseId,teacherId);
    }

}
