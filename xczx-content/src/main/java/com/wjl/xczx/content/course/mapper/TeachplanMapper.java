package com.wjl.xczx.content.course.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjl.xczx.content.course.model.entity.Teachplan;
import com.wjl.xczx.content.course.model.vo.TeachplanVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
public interface TeachplanMapper extends BaseMapper<Teachplan> {

    List<TeachplanVO> getTeachplanVOsByCourseId(@Param("courseId") Long courseId);

    Boolean deleteTeachplanAndChild(@Param("id") Long id);

    /**
     * 获取当前节点的后面一个节点
     *
     * @param orderby
     * @param parentid
     * @return
     */
    Optional<Teachplan> afterOrderBy(@Param("orderby") Integer orderby, @Param("parentid") Long parentid);

    /**
     * 获取当前节点的前面一个节点
     *
     * @param orderby
     * @param parentid
     * @return
     */
    Optional<Teachplan> beforeOrderBy(@Param("orderby") Integer orderby, @Param("parentid") Long parentid);

    @Select("select id from teachplan where course_id = #{courseId}")
    List<Long> getTeachplanIdsByCourseId(@Param("courseId") Long courseId);
}
