package com.wjl.xczx.content.course.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjl.xczx.common.consts.XczxConstant;
import com.wjl.xczx.common.exception.Assert;
import com.wjl.xczx.content.course.exception.CourseException;
import com.wjl.xczx.content.course.exception.state.CourseStateEnum;
import com.wjl.xczx.content.course.mapper.CourseMarketMapper;
import com.wjl.xczx.content.course.model.entity.CourseMarket;
import com.wjl.xczx.content.course.service.CourseMarketService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
@Service
public class CourseMarketServiceImpl extends ServiceImpl<CourseMarketMapper, CourseMarket> implements CourseMarketService {

    @Transactional
    @Override
    public void saveNewMartket(CourseMarket courseMarket) {
        String charge = courseMarket.getCharge();
        Assert.isTrue(!StringUtils.isEmpty(charge), () -> new CourseException(CourseStateEnum.CHARGE_ERROR));

        // 课程收费 价格错误也将添加失败
        if (XczxConstant.ChargeModel.TOLL.getCode().equals(charge)) {
            boolean b = courseMarket.getPrice() == null || courseMarket.getPrice().compareTo(new BigDecimal("0")) != 1;
            Assert.isTrue(!StringUtils.isEmpty(charge), () -> new CourseException(CourseStateEnum.PRICE_ERROR));
        }

        CourseMarket market = getById(courseMarket.getId());
        boolean i;
        if (market != null) {
            BeanUtils.copyProperties(courseMarket, market);
            i = updateById(market);
        } else {
            i = save(courseMarket);
        }
        Assert.isTrue(i, () -> new CourseException(CourseStateEnum.ADD_ERROR));

    }

}
