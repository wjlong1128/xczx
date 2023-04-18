package com.wjl.xczx.system.mapper;

import com.wjl.xczx.system.model.Dictionary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
@SpringBootTest
public class DictionaryMapperTest {

    @Autowired
    private DictionaryMapper mapper;

    @Test
    void selectAll() {
        List<Dictionary> list = mapper.selectList(null);
        Assert.notEmpty(list,"not null");
        list.forEach(System.out::println);
    }
}
