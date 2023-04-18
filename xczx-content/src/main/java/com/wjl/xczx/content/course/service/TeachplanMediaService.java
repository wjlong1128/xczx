package com.wjl.xczx.content.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjl.xczx.content.course.model.entity.TeachplanMedia;

import java.util.List;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
public interface TeachplanMediaService extends IService<TeachplanMedia> {
    /**
     *  批量删除媒资信息
     * @param ids
     */
    void deleteByTeachPlanIds(List<Long> ids);
}
