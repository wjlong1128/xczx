package com.wjl.xczx.system.controller;

import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.system.service.DictionaryService;
import com.wjl.xczx.system.model.vo.DictionaryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */

@RestController
@RequestMapping("dictionary")
@RequiredArgsConstructor
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @GetMapping("all")
    public Result<List<DictionaryVO>> all(){
        return dictionaryService.all();
    }

    @GetMapping("/code/{code}")
    public Result<DictionaryVO> getByCode(@PathVariable("code") String code){
        return dictionaryService.getByCode(code);
    }

}
