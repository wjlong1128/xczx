package com.wjl.xczx.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.system.mapper.DictionaryMapper;
import com.wjl.xczx.system.model.Dictionary;
import com.wjl.xczx.system.model.vo.DictionaryVO;
import com.wjl.xczx.system.service.DictionaryService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements DictionaryService {
    @Override
    public Result<List<DictionaryVO>> all() {
        List<DictionaryVO> voList = list().stream().map(item -> {
            DictionaryVO vo = new DictionaryVO();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).collect(Collectors.toList());
        return Result.success(voList);
    }

    @Override
    public Result<DictionaryVO> getByCode(String code) {
        AbstractWrapper<Dictionary, SFunction<Dictionary, ?>, LambdaQueryWrapper<Dictionary>> wrapper =
                this
                        .lambdaQuery()
                        .eq(!StringUtils.isEmpty(code), Dictionary::getCode, code)
                        .getWrapper();
        Dictionary dictionary = this.getOne(wrapper);
        DictionaryVO vo = new DictionaryVO();
        BeanUtils.copyProperties(dictionary, vo);
        return Result.success(vo);
    }
}
