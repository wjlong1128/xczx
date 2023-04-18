package com.wjl.xczx.content.course.dao;

import com.wjl.xczx.content.course.mapper.TeachplanMapper;
import com.wjl.xczx.content.course.model.entity.Teachplan;
import com.wjl.xczx.content.course.model.vo.TeachplanVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
@SpringBootTest
public class TeachplanMapperTest {

    @Autowired
    private TeachplanMapper mapper;

    @Test
    void getTeachplanVOsByCourseId() {
        List<TeachplanVO> list = mapper.getTeachplanVOsByCourseId(117L);
        list.forEach(System.out::println);
    }

    @Test
    void test() {
        Optional<Teachplan> order = mapper.afterOrderBy(1, 43L);
        System.out.println(order.get());
    }

    @Test
    void getIds() {
        List<Long> ids = mapper.getTeachplanIdsByCourseId(117L);
        System.out.println(ids);
    }
}
