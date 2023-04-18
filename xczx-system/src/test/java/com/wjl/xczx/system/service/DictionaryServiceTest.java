package com.wjl.xczx.system.service;

import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.system.model.vo.DictionaryVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
@SpringBootTest
public class DictionaryServiceTest {

    @Autowired
    private DictionaryService dictionaryService;

    @Test
    void getByCode() {
        Result<DictionaryVO> result = dictionaryService.getByCode("000");
        DictionaryVO data = result.getData();
        Assert.notNull(data,"not null");
        System.out.println(data);
    }
}
