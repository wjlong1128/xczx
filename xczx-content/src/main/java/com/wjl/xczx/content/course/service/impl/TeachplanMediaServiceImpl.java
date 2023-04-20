package com.wjl.xczx.content.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjl.xczx.common.exception.Assert;
import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.content.course.exception.CourseException;
import com.wjl.xczx.content.course.exception.state.CourseStateEnum;
import com.wjl.xczx.content.course.mapper.TeachplanMediaMapper;
import com.wjl.xczx.content.course.model.dto.BindTeachPlanMediaDTO;
import com.wjl.xczx.content.course.model.entity.Teachplan;
import com.wjl.xczx.content.course.model.entity.TeachplanMedia;
import com.wjl.xczx.content.course.service.TeachplanMediaService;
import com.wjl.xczx.content.course.service.TeachplanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
@Service
@RequiredArgsConstructor
public class TeachplanMediaServiceImpl extends ServiceImpl<TeachplanMediaMapper, TeachplanMedia> implements TeachplanMediaService {
    @Autowired
    @Lazy
    private  TeachplanService teachplanService;

    @Transactional
    @Override
    public void deleteByTeachPlanIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        // 删除0条失败
        remove(new LambdaQueryWrapper<TeachplanMedia>().in(TeachplanMedia::getTeachplanId, ids));
    }

    @Transactional
    @Override
    public void associationMedia(BindTeachPlanMediaDTO bindTeachPlanMediaDTO) {
        if (bindTeachPlanMediaDTO == null){
            return;
        }
        // 先删除原有绑定记录，再插入新记录
        LambdaQueryWrapper<TeachplanMedia> wrapper = new LambdaQueryWrapper<>();
        Long teachplanId = bindTeachPlanMediaDTO.getTeachplanId();
        wrapper.eq(TeachplanMedia::getTeachplanId, teachplanId);
        this.remove(wrapper);
        TeachplanMedia media = new TeachplanMedia();
        Teachplan teachplan = teachplanService.getById(bindTeachPlanMediaDTO.getTeachplanId());
        Assert.notNull(teachplan,()-> new CourseException(CourseStateEnum.TECH_QUERY_NOT_NULL_ERROR));
        media.setCourseId(teachplan.getCourseId());
        media.setMediaId(bindTeachPlanMediaDTO.getMediaId());
        media.setMediaFilename(bindTeachPlanMediaDTO.getFileName());
        media.setTeachplanId(bindTeachPlanMediaDTO.getTeachplanId());
        save(media);
    }

    @Override
    public Result unAssociationMedia(Long teachplanId, String mediaId) {
        LambdaQueryWrapper<TeachplanMedia> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TeachplanMedia::getTeachplanId,teachplanId).eq(TeachplanMedia::getMediaId,mediaId);
        this.remove(wrapper);
        return Result.success();
    }
}
