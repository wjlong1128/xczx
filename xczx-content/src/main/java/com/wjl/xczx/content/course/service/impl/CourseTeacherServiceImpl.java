package com.wjl.xczx.content.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjl.xczx.common.exception.Assert;
import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.common.result.status.StateEnum;
import com.wjl.xczx.content.course.exception.CourseException;
import com.wjl.xczx.content.course.exception.state.CourseStateEnum;
import com.wjl.xczx.content.course.mapper.CourseTeacherMapper;
import com.wjl.xczx.content.course.model.dto.TeacherDTO;
import com.wjl.xczx.content.course.model.entity.CourseTeacher;
import com.wjl.xczx.content.course.model.vo.TeacherVO;
import com.wjl.xczx.content.course.service.CourseTeacherService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
@Service
public class CourseTeacherServiceImpl extends ServiceImpl<CourseTeacherMapper, CourseTeacher> implements CourseTeacherService {
    @Override
    public Result<List<TeacherVO>> listByCourseId(Long companyId,Long courseId) {
        Assert.notNull(companyId,()-> new CourseException(StateEnum.NOT_AUTHENTICATION));
        Assert.notNull(courseId,()-> new CourseException(CourseStateEnum.QUERY_ERROR));
        List<TeacherVO> voList = getTeacherVOS(courseId);
        return Result.success(voList);
    }

    private List<TeacherVO> getTeacherVOS(Long courseId) {
        List<CourseTeacher> list = this.list(new LambdaQueryWrapper<CourseTeacher>().eq(CourseTeacher::getCourseId, courseId));
        List<TeacherVO> voList = list.stream().map(item -> {
            TeacherVO teacherVO = new TeacherVO();
            BeanUtils.copyProperties(item, teacherVO);
            return teacherVO;
        }).collect(Collectors.toList());
        return voList;
    }

    @Transactional
    @Override
    public Result<TeacherVO> saveTeacher(Long companyId,TeacherDTO teacherDTO) {
       Assert.notNull(companyId,()-> new CourseException(StateEnum.NOT_AUTHENTICATION));
        if(teacherDTO.getId() != null){
            CourseTeacher teacher = new CourseTeacher();
            BeanUtils.copyProperties(teacherDTO,teacher);
            boolean update = updateById(teacher);
            Assert.isTrue(update,()-> new CourseException(CourseStateEnum.UPDATE_TEACHER_ERROR));
            TeacherVO teacherVO = new TeacherVO();
            BeanUtils.copyProperties(this.getById(teacher.getId()),teacherVO);
            return Result.success(teacherVO);
        }

        CourseTeacher teacher = new CourseTeacher();
        BeanUtils.copyProperties(teacherDTO,teacher);
        boolean save = save(teacher);
        Assert.isTrue(save,()-> new CourseException(CourseStateEnum.ADD_TEACHER_ERROR));

        TeacherVO data = new TeacherVO();
        BeanUtils.copyProperties(getById(teacher.getId()),data);
        return Result.success(data);
    }

    @Override
    public Result<Object> deleteByCourseId(Long companyId,Long courseId, Long teacherId) {
         Assert.notNull(companyId,()-> new CourseException(StateEnum.NOT_AUTHENTICATION));
        boolean remove = this.remove(new LambdaQueryWrapper<CourseTeacher>().eq(CourseTeacher::getCourseId, courseId).eq(CourseTeacher::getId, teacherId));
        Assert.isTrue(remove,()-> new CourseException(CourseStateEnum.DEL_TEACHER_ERROR));
        return Result.success();
    }

    @Override
    public List<TeacherVO> courseTeacher(Long courseId) {
        List<TeacherVO> voList = getTeacherVOS(courseId);
        return voList;
    }
}
