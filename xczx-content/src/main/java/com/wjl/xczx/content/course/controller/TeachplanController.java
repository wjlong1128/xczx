package com.wjl.xczx.content.course.controller;

import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.content.course.model.dto.AddTeachplanDTO;
import com.wjl.xczx.content.course.model.vo.TeachplanVO;
import com.wjl.xczx.content.course.service.TeachplanService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
@RequestMapping("teachplan")
@RestController
@RequiredArgsConstructor
public class TeachplanController {

    private final TeachplanService teachplanService;

    @GetMapping("/{courseId}/tree-nodes")
    public Result<List<TeachplanVO>> treeNodes(@PathVariable("courseId") Long courseId) {
        return teachplanService.treeNodes(courseId);
    }

    /**
     *  新增或者修改章节
     * @param addTeachplanDTO
     * @return
     */
    @PostMapping
    public Result<Object> saveOrUpdate(@RequestBody @Validated AddTeachplanDTO addTeachplanDTO){
        return teachplanService.addOrUpdate(addTeachplanDTO);
    }

    /**
     *  删除章节
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public Result<Object> delete(@PathVariable ("id") Long id){
        return teachplanService.deleteById(id);
    }

    // 移动

    @PostMapping("moveup/{id}")
    public Result<Object> moveup(@PathVariable("id") Long id){
        return teachplanService.moveup(id);
    }

    @PostMapping("movedown/{id}")
    public Result<Object> movedown(@PathVariable("id") Long id){
        return teachplanService.movedown(id);
    }
}
