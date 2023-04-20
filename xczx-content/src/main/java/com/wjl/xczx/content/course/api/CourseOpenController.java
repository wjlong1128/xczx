package com.wjl.xczx.content.course.api;

import com.wjl.xczx.content.course.model.vo.CoursePreviewVO;
import com.wjl.xczx.content.course.service.CourseBaseService;
import com.wjl.xczx.content.course.service.CoursePublishService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/18
 * @description
 */
@RequestMapping("open")
@RestController
@RequiredArgsConstructor
public class CourseOpenController {

    private final CoursePublishService coursePublishService;

    private final CourseBaseService courseBaseService;

    @GetMapping("course/whole/{courseId}")
    public CoursePreviewVO getCourseInfo(@PathVariable("courseId") Long courseId){
        return coursePublishService.coursePreview(courseId);
    }


}
