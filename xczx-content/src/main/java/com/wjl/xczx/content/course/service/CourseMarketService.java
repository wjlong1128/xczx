package com.wjl.xczx.content.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjl.xczx.content.course.model.entity.CourseMarket;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
public interface CourseMarketService extends IService<CourseMarket> {
    /**
     *  逻辑判断 存在更新，不存在添加
     * @param courseMarket
     */
    void saveNewMartket(CourseMarket courseMarket);
}
