package com.wjl.xczx.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.system.model.Dictionary;
import com.wjl.xczx.system.model.vo.DictionaryVO;

import java.util.List;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
public interface DictionaryService extends IService<Dictionary> {
    Result<List<DictionaryVO>> all();

    Result<DictionaryVO> getByCode(String code);

}
