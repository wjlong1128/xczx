package com.wjl.xczx.content.course.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjl.xczx.content.XczxContentApplication;
import com.wjl.xczx.content.course.mapper.CourseBaseMapper;
import com.wjl.xczx.content.course.model.dto.QueryCourseParamsDTO;
import com.wjl.xczx.content.course.model.entity.CourseBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @description
 * @date 2023/4/14
 */
@SpringBootTest(classes = XczxContentApplication.class)
public class CourseBaseDaoTest {

    @Autowired
    private CourseBaseMapper mapper;

    @Test
    void testMapper() {
        System.out.println(mapper);
    }

    /**
     *  查询测试
     */
    @Test
    void selectList() {
        List<CourseBase> list = mapper.selectList(null);
        Assert.notNull(list,()->"list not null");
        System.out.println(list);
    }

    /**
     * 条件查询
     */
    @Test
    void queryCourseWithCondition() {
        QueryCourseParamsDTO params = new QueryCourseParamsDTO();
        //params.setCourseName("JAVA");
        params.setPublishStatus("203001");
        List<CourseBase> list = mapper.queryCourseWithCondition(117L,params, new Page(1, 5)).getRecords();
        Assert.notNull(list,"集合不能为空");
        System.out.println(list.size());
        for (CourseBase courseBase : list) {
            System.out.println(courseBase);
        }
    }
}
