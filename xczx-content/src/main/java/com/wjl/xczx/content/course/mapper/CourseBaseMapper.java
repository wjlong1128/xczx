package com.wjl.xczx.content.course.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wjl.xczx.content.course.model.dto.QueryCourseParamsDTO;
import com.wjl.xczx.content.course.model.entity.CourseBase;
import org.apache.ibatis.annotations.Param;


/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @description
 * @date 2023/4/14
 */
// @Mapper
public interface CourseBaseMapper extends BaseMapper<CourseBase> {

    /**
     *  单表条件查询
     * @param params
     * @param page
     * @return
     */
    IPage<CourseBase> queryCourseWithCondition(@Param("params") QueryCourseParamsDTO params, IPage page);

}
