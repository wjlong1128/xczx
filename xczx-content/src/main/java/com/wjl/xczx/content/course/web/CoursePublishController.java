package com.wjl.xczx.content.course.web;

import com.wjl.xczx.common.result.RestResponse;
import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.content.config.GlobalConst;
import com.wjl.xczx.content.course.model.vo.CoursePreviewVO;
import com.wjl.xczx.content.course.service.CoursePublishService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/18
 * @description 课程发布
 */
@Controller
@RequiredArgsConstructor
public class CoursePublishController {

    private final CoursePublishService coursePublishService;

    /**
     *  课程预览
     * @param courseId
     * @param model
     * @return
     */
    @GetMapping("/coursepreview/{courseId}")
    public String coursePreview(@PathVariable("courseId") Long courseId, Model model){
        CoursePreviewVO previewVO = coursePublishService.coursePreview(courseId);
        model.addAttribute("model",previewVO);
        return "course_template";
    }

    /**
     *  提交审核
     * @param course
     * @return
     */
    @ResponseBody
    @PostMapping("/courseaudit/commit/{courseId}")
    public RestResponse commitAudit(@PathVariable("courseId") Long course){
        Long companyId = GlobalConst.COMPANY_ID;
        coursePublishService.commitAudit(companyId,course);
        return RestResponse.success();
    }


    @ResponseBody
    @PostMapping("coursepublish/{courseId}")
    public Result coursePublish(@PathVariable("courseId") Long courseId){
        Long companyId = GlobalConst.COMPANY_ID;
        coursePublishService.publish(companyId,courseId);
        return Result.success();
    }

}
