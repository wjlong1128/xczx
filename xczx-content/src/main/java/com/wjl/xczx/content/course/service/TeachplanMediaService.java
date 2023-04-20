package com.wjl.xczx.content.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.content.course.model.dto.BindTeachPlanMediaDTO;
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

    /**
     *  绑定课程计划与媒资
     * @param bindTeachPlanMediaDTO
     */
    void associationMedia(BindTeachPlanMediaDTO bindTeachPlanMediaDTO);

    /**
     *  接触绑定
     * @param teachplanId
     * @param mediaId
     * @return
     */
    Result unAssociationMedia(Long teachplanId, String mediaId);
}
