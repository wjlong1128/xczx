package com.wjl.xczx.content.course.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.content.course.mapper.CourseCategoryMapper;
import com.wjl.xczx.content.course.model.entity.CourseCategory;
import com.wjl.xczx.content.course.model.vo.CourseCategoryTreeVO;
import com.wjl.xczx.content.course.service.CourseCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
@Service
public class CourseCategoryServiceImpl extends ServiceImpl<CourseCategoryMapper,CourseCategory> implements CourseCategoryService {
    @Override
    public Result<List<CourseCategoryTreeVO>> treeNodes() {
        List<CourseCategory> categoryList = list();
        List<CourseCategoryTreeVO> voList = categoryList.stream().map(item -> {
            CourseCategoryTreeVO vo = new CourseCategoryTreeVO();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).collect(Collectors.toList());

        List<CourseCategoryTreeVO> nodes = getChildNodes(voList,"1");

        return Result.success(nodes);
    }

    @Override
    public String getCategoryNameById(String id) {
        if(StringUtils.hasText(id)){
            CourseCategory category = this.getById(id);
            if (category!=null){
                return category.getName();
            }

        }
        return null;
    }

    /**
     *  根据传递的父id查找该节点所有的子节点
     * @param voList 原始集合
     * @param parentId
     * @return
     */
    private List<CourseCategoryTreeVO> getChildNodes(List<CourseCategoryTreeVO> voList,String parentId){
        if(CollectionUtils.isEmpty(voList) || !StringUtils.hasText(parentId)){
            return null;
        }
        // 1. 从集合中过滤掉所有的父节点 作为下一轮筛选的集合
        List<CourseCategoryTreeVO> currentList = voList.stream().filter(item -> !item.getParentid().equals(parentId)).collect(Collectors.toList());

        // 2. 过滤出当前parentId下所有的子节点
        List<CourseCategoryTreeVO> courseCategoryTreeVOS = voList
                .stream()
                .filter(vo -> vo.getParentid().equals(parentId))
                .sorted((o1, o2) -> {
                    return (o1.getOrderby() != null? o1.getOrderby():0) - (o2.getOrderby() != null? o2.getOrderby():0);
                })
                .sorted((o1, o2) -> o1.getId().compareTo(o2.getId()))
                .map(vo -> {
                    vo.getParentid().equals(parentId);
                    List<CourseCategoryTreeVO> childNodes = getChildNodes(currentList, vo.getId());
                    vo.setChildrenTreeNodes(childNodes);
                    return vo;
                }).toList();

        return courseCategoryTreeVOS;
    }
}
