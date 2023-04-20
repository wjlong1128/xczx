package com.wjl.xczx.content.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjl.xczx.common.exception.Assert;
import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.common.result.status.StateEnum;
import com.wjl.xczx.content.course.exception.CourseException;
import com.wjl.xczx.content.course.exception.state.CourseStateEnum;
import com.wjl.xczx.content.course.mapper.TeachplanMapper;
import com.wjl.xczx.content.course.model.dto.AddTeachplanDTO;
import com.wjl.xczx.content.course.model.entity.Teachplan;
import com.wjl.xczx.content.course.model.vo.TeachplanVO;
import com.wjl.xczx.content.course.service.TeachplanMediaService;
import com.wjl.xczx.content.course.service.TeachplanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
@RequiredArgsConstructor
@Service
public class TeachplanServiceImpl extends ServiceImpl<TeachplanMapper, Teachplan> implements TeachplanService {


    private final TeachplanMediaService teachplanMediaService;



    @Override
    public Result<List<TeachplanVO>> treeNodes(Long courseId) {
        //Assert.notNull(companyId,()-> new CourseException(StateEnum.NOT_AUTHENTICATION));
        List<TeachplanVO> teachplanVOList = this.baseMapper.getTeachplanVOsByCourseId(courseId);
        return Result.success(teachplanVOList);
    }

    @Transactional
    @Override
    public Result<Object> addOrUpdate(Long companyId,AddTeachplanDTO addTeachplanDTO) {
        Assert.notNull(companyId,()-> new CourseException(StateEnum.NOT_AUTHENTICATION));
        Long id = addTeachplanDTO.getId();
        if (id == null) {
            Teachplan entity = new Teachplan();
            BeanUtils.copyProperties(addTeachplanDTO, entity);
            // 排序字段 这里的话由前端计算出来更好
            entity.setOrderby(getOrder(entity.getParentid()));
            boolean save = save(entity);
            Assert.isTrue(save, () -> new CourseException(CourseStateEnum.TECH_ERROR));
            return Result.success();
        }

        Teachplan entity = new Teachplan();
        BeanUtils.copyProperties(addTeachplanDTO, entity);
        boolean update = updateById(entity);
        Assert.isTrue(update, () -> new CourseException(CourseStateEnum.TECH_UPDATE_ERROR));
        return Result.success();
    }

    @Transactional
    @Override
    public Result<Object> deleteById(Long companyId,Long id) {
        Assert.notNull(companyId,()-> new CourseException(StateEnum.NOT_AUTHENTICATION));
        Boolean delete = this.baseMapper.deleteTeachplanAndChild(id);
        Assert.isTrue(Boolean.TRUE.equals(delete), () -> new CourseException(CourseStateEnum.TECH_DELETE_ERROR));
        return Result.okMessage("删除成功");
    }

    @Override
    public int getOrder(Long parentId) {
        long count = count(new LambdaQueryWrapper<Teachplan>().eq(Teachplan::getParentid, parentId));
        return (int) count + 1;
    }

    /**
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Result<Object> moveup(Long companyId,Long id) {
        Assert.notNull(companyId,()-> new CourseException(StateEnum.NOT_AUTHENTICATION));
        Teachplan teachplan = getById(id);
        Integer orderby = teachplan.getOrderby();
        Optional<Teachplan> beforeOption = this.baseMapper.beforeOrderBy(orderby, teachplan.getParentid());
        Teachplan before = null;
        try {
            before = beforeOption.get();
        } catch (Exception e) {
            return Result.success();
        }
        teachplan.setOrderby(before.getOrderby());
        before.setOrderby(orderby);
        boolean updateBatchById = updateBatchById(Arrays.asList(teachplan, before));
        Assert.isTrue(updateBatchById, () -> new CourseException(CourseStateEnum.MOVE_FAIL));
        return Result.success();
    }

    /**
     * 首先判断是否最后一位
     * 1. 是-> 不处理
     * 2. 不是 -> 查出后面一位 同时更新两个节点的排序
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Result<Object> movedown(Long companyId,Long id) {
        Assert.notNull(companyId,()-> new CourseException(StateEnum.NOT_AUTHENTICATION));
        Teachplan current = getById(id);
        int order = getOrder(current.getParentid());
        // 已经是最后面了
        if (order == current.getOrderby() + 1) {
            return Result.success();
        }
        // SELECT orderby FROM teachplan WHERE parentid = 43 AND orderby > 1 ORDER BY orderby LIMIT 1
        Optional<Teachplan> teachplanOptional = this.baseMapper.afterOrderBy(current.getOrderby(), current.getParentid());
        Teachplan teachplan = teachplanOptional.orElseThrow(() -> new CourseException(CourseStateEnum.MOVE_FAIL));
        Integer orderby = teachplan.getOrderby();
        teachplan.setOrderby(current.getOrderby());
        current.setOrderby(orderby);
        boolean updateBatchById = updateBatchById(Arrays.asList(teachplan, current));
        Assert.isTrue(updateBatchById, () -> new CourseException(CourseStateEnum.MOVE_FAIL));
        return Result.success();
    }

    @Transactional
    @Override
    public void deleteTeachplanAndMediaByCourseId(Long companyId,Long courseId) {
        Assert.notNull(companyId,()-> new CourseException(StateEnum.NOT_AUTHENTICATION));
        List<Long> ids = this.baseMapper.getTeachplanIdsByCourseId(courseId);
        if(CollectionUtils.isEmpty(ids)){
            return;
        }

        boolean remove = removeBatchByIds(ids);
        // Assert.isTrue(remove,()-> new CourseException(CourseStateEnum.DEL_TEACH_BATCH_BY_COURSE_ERROR));

        // 根据 课程计划id删除对应的媒资
        teachplanMediaService.deleteByTeachPlanIds(ids);
    }
}
