package com.wjl.xczx.content.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjl.xczx.common.consts.XczxConstant;
import com.wjl.xczx.common.exception.Assert;
import com.wjl.xczx.common.result.PageResult;
import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.common.result.status.StateEnum;
import com.wjl.xczx.common.vo.PageParams;
import com.wjl.xczx.content.course.exception.CourseException;
import com.wjl.xczx.content.course.exception.state.CourseStateEnum;
import com.wjl.xczx.content.course.mapper.CourseBaseMapper;
import com.wjl.xczx.content.course.model.dto.AddCourseDTO;
import com.wjl.xczx.content.course.model.dto.EditCourseDTO;
import com.wjl.xczx.content.course.model.dto.QueryCourseParamsDTO;
import com.wjl.xczx.content.course.model.entity.CourseBase;
import com.wjl.xczx.content.course.model.entity.CourseMarket;
import com.wjl.xczx.content.course.model.vo.CourseInfoVO;
import com.wjl.xczx.content.course.model.vo.CourseVO;
import com.wjl.xczx.content.course.service.CourseBaseService;
import com.wjl.xczx.content.course.service.CourseCategoryService;
import com.wjl.xczx.content.course.service.CourseMarketService;
import com.wjl.xczx.content.course.service.TeachplanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.wjl.xczx.content.course.exception.state.CourseStateEnum.EDIT_ERROR;
import static com.wjl.xczx.content.course.exception.state.CourseStateEnum.UNAUTHORIZED_ERROR;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @description
 * @date 2023/4/14
 */
@RequiredArgsConstructor
@Service
public class CourseBaseServiceImpl extends ServiceImpl<CourseBaseMapper, CourseBase> implements CourseBaseService {

    private final CourseBaseMapper courseBaseMapper;

    private final CourseMarketService courseMarketService;

    private final CourseCategoryService courseCategoryService;

    private final TeachplanService teachplanService;

    @Override
    public Result<PageResult<CourseVO>> queryCourseWithCondition(Long companyId,PageParams params, QueryCourseParamsDTO queryCourseParamsDTO) {
        Assert.notNull(companyId,()-> new CourseException(StateEnum.NOT_AUTHENTICATION));
        IPage<CourseBase> page;
        Page<CourseBase> pageParam = new Page<>(params.getPageNo().longValue(), params.getPageSize().longValue());
        if (!ObjectUtils.isEmpty(queryCourseParamsDTO)) {
            page = courseBaseMapper.queryCourseWithCondition(companyId, queryCourseParamsDTO, pageParam);
        } else {
            LambdaQueryWrapper<CourseBase> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(CourseBase::getCompanyId,companyId);
            page = courseBaseMapper.selectPage(pageParam, wrapper);
        }
        List<CourseVO> courseVOList = page.getRecords().stream().map(item -> {
            CourseVO vo = new CourseVO();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).collect(Collectors.toList());
        return Result.success(new PageResult<>(page.getCurrent(), page.getSize(), page.getTotal(), courseVOList));
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<CourseInfoVO> addCourse(Long companyId,AddCourseDTO addCourseDTO) {
        Assert.notNull(companyId,()-> new CourseException(StateEnum.NOT_AUTHENTICATION));
        CourseBase newCourse = new CourseBase();
        newCourse.setCompanyId(companyId);
        BeanUtils.copyProperties(addCourseDTO, newCourse);
        newCourse.setStatus(XczxConstant.PublishStatus.NOT.getCode());
        newCourse.setAuditStatus(XczxConstant.AuditStatus.NOT_COMMIT.getCode());
        boolean save = save(newCourse);
        Assert.isTrue(save, () -> new CourseException(CourseStateEnum.ADD_ERROR));

        Long newCourseId = newCourse.getId();
        CourseMarket courseMarket = new CourseMarket();
        BeanUtils.copyProperties(addCourseDTO, courseMarket);
        courseMarket.setId(newCourseId);
        courseMarketService.saveNewMartket(courseMarket);
        CourseInfoVO data = getCourseInfoVO(companyId,newCourseId);
        return Result.success(data);
    }

    private CourseInfoVO getCourseInfoVO(Long companyId,Long newCourseId) {
        CourseInfoVO data = new CourseInfoVO();
        LambdaQueryWrapper<CourseBase> wrapper = new LambdaQueryWrapper<>();
        if (companyId != null){ // 区分机构端和客户端
            wrapper.eq(CourseBase::getCompanyId,companyId);
        }

        wrapper.eq(CourseBase::getId,newCourseId);
        CourseBase courseBase = this.getOne(wrapper);
        CourseMarket courseMarket = courseMarketService.getById(newCourseId);
        Assert.isTrue(courseBase != null && courseMarket != null, () -> new CourseException(CourseStateEnum.QUERY_ERROR));
        BeanUtils.copyProperties(courseBase, data);
        BeanUtils.copyProperties(courseMarket, data);
        // 查询分类名称
        String mtName = courseCategoryService.getCategoryNameById(data.getMt());
        data.setMtName(mtName);
        String stName = courseCategoryService.getCategoryNameById(data.getSt());
        data.setStName(stName);
        return data;
    }

    @Override
    public Result<CourseInfoVO> getCourseById(Long companyId,Long courseId) {
        Assert.notNull(companyId,()-> new CourseException(StateEnum.NOT_AUTHENTICATION));
        CourseInfoVO courseInfoVO = getCourseInfoVO(companyId,courseId);
        return Result.success(courseInfoVO);
    }

    @Transactional
    @Override
    public Result<CourseInfoVO> editCourse(Long companyId, EditCourseDTO editCourseDTO) {
        Assert.notNull(companyId,()-> new CourseException(StateEnum.NOT_AUTHENTICATION));
        CourseBase courseBase = getById(editCourseDTO.getId());
        CourseMarket market = courseMarketService.getById(editCourseDTO.getId());
        Assert.notNull(courseBase, () -> new CourseException(EDIT_ERROR));
        Assert.notNull(market, () -> new CourseException(EDIT_ERROR));


        if (!courseBase.getCompanyId().equals(companyId)) {
            throw new CourseException(UNAUTHORIZED_ERROR);
        }

        BeanUtils.copyProperties(editCourseDTO, courseBase);
        BeanUtils.copyProperties(editCourseDTO, market);
        boolean update = updateById(courseBase);
        Assert.isTrue(update, () -> new CourseException(EDIT_ERROR));
        courseMarketService.saveNewMartket(market);

        CourseInfoVO infoVO = getCourseInfoVO(companyId,courseBase.getId());
        return Result.success(infoVO);
    }

    @Transactional
    @Override
    public Result<Object> deleteCourse(Long companyId,Long courseId) {
        Assert.notNull(companyId,()-> new CourseException(StateEnum.NOT_AUTHENTICATION));
        CourseBase courseBase = getById(courseId);
        Assert.notNull(courseBase,()-> new CourseException(CourseStateEnum.NOT_DEL_COURSE));
        String auditStatus = courseBase.getAuditStatus();
        if(!XczxConstant.AuditStatus.NOT_COMMIT.getCode().equals(auditStatus)){
            throw  new CourseException(CourseStateEnum.DEL_COURSE_STATE_ERROR);
        }
        LambdaQueryWrapper<CourseBase> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CourseBase::getCompanyId,companyId).eq(CourseBase::getId,courseId);
        boolean remove = remove(wrapper);
        Assert.isTrue(remove,()-> new CourseException(CourseStateEnum.DEL_COURSE_ERROR));

        // 删除相关营销信息
        courseMarketService.remove(new LambdaQueryWrapper<CourseMarket>().eq(CourseMarket::getId, courseId));
        // Assert.isTrue(remove,()-> new CourseException(CourseStateEnum.DEL_MARKET_ERROR));

        // 删除课程计划
        teachplanService.deleteTeachplanAndMediaByCourseId(companyId,courseId);
        return Result.success();
    }


    @Override
    public CourseInfoVO getCourseById(Long courseId) {
        return getCourseInfoVO(null,courseId);
    }
}
