package com.wjl.xczx.content.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjl.xczx.content.course.mapper.TeachplanMediaMapper;
import com.wjl.xczx.content.course.model.entity.TeachplanMedia;
import com.wjl.xczx.content.course.service.TeachplanMediaService;
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
public class TeachplanMediaServiceImpl extends ServiceImpl<TeachplanMediaMapper, TeachplanMedia> implements TeachplanMediaService {
    @Transactional
    @Override
    public void deleteByTeachPlanIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        // 删除0条失败
        remove(new LambdaQueryWrapper<TeachplanMedia>().in(TeachplanMedia::getTeachplanId, ids));
    }
}
