package com.wjl.xczx.content.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.content.course.model.dto.AddTeachplanDTO;
import com.wjl.xczx.content.course.model.entity.Teachplan;
import com.wjl.xczx.content.course.model.vo.TeachplanVO;

import java.util.List;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
public interface TeachplanService extends IService<Teachplan> {
    /**
     * 根据id查询课程计划
     *
     * @param courseId
     * @return
     */
    Result<List<TeachplanVO>> treeNodes(Long courseId);

    /**
     *  新增或者修改章节
     * @param addTeachplanDTO
     * @return
     */
    Result<Object> addOrUpdate(AddTeachplanDTO addTeachplanDTO);

    /**
     *  删除章节，并且删除子章节
     * @param id
     * @return
     */
    Result<Object> deleteById(Long id);

    /**
     *  获取排序
     * @param id
     * @return
     */
    int getOrder(Long id);

    /**
     *  上移
     * @param id
     * @return
     */
    Result<Object> moveup(Long id);

    /**
     *  下移
     * @param id
     * @return
     */
    Result<Object> movedown(Long id);

    /**
     *  根据课程id删除 课程计划与相关媒资
     * @param courseId
     */
    void deleteTeachplanAndMediaByCourseId(Long courseId);
}
