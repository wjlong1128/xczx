package com.wjl.xczx.content.course.api;

import com.wjl.xczx.common.result.PageResult;
import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.common.vo.PageParams;
import com.wjl.xczx.content.config.GlobalConst;
import com.wjl.xczx.content.course.model.dto.AddCourseDTO;
import com.wjl.xczx.content.course.model.dto.EditCourseDTO;
import com.wjl.xczx.content.course.model.dto.QueryCourseParamsDTO;
import com.wjl.xczx.content.course.model.vo.CourseInfoVO;
import com.wjl.xczx.content.course.model.vo.CourseVO;
import com.wjl.xczx.content.course.service.CourseBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @description
 * @date 2023/4/14
 */
@RequestMapping("course")
@RestController
public class CourseBaseInfoController {

    @Autowired
    private CourseBaseService courseBaseService;

    //@GetMapping("/test")
    //public LocalDateTime test(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime data){
    //    return data;
    //}

    @PostMapping("list")
    public Result<PageResult<CourseVO>> list(PageParams params, @RequestBody(required = false) QueryCourseParamsDTO queryCourseParamsDTO) {
        Long companyId = GlobalConst.COMPANY_ID;
        return courseBaseService.queryCourseWithCondition(companyId,params, queryCourseParamsDTO);
    }

    /**
     * 新增课程
     */
    @PostMapping
    public Result<CourseInfoVO> addCourse(@RequestBody @Validated AddCourseDTO addCourseDTO) {
        Long companyId = GlobalConst.COMPANY_ID;
        return courseBaseService.addCourse(companyId,addCourseDTO);
    }

    /**
     * 修改课程
     * @param editCourseDTO
     * @return
     */
    @PutMapping
    public Result<CourseInfoVO> editCourse(@RequestBody @Validated EditCourseDTO editCourseDTO){
        // TODO 机构id
        Long companyId = GlobalConst.COMPANY_ID;
        return courseBaseService.editCourse(companyId,editCourseDTO);
    }

    /**
     *  查询
     * @param courseId
     * @return
     */
    @GetMapping("{courseId}")
    public Result<CourseInfoVO> getCourseById(@PathVariable("courseId") Long courseId){
        Long companyId = GlobalConst.COMPANY_ID;
        return courseBaseService.getCourseById(companyId,courseId);
    }

    /**
     *  删除课程及其相关信息
     */
    @DeleteMapping("{courseId}")
    public Result<Object> deleteCourse(@PathVariable("courseId") Long courseId){
        Long companyId = GlobalConst.COMPANY_ID;
        return courseBaseService.deleteCourse(companyId,courseId);
    }



}
