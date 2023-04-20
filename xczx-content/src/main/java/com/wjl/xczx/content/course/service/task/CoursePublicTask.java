package com.wjl.xczx.content.course.service.task;

import com.wjl.xczx.content.course.service.CoursePublishService;
import com.wjl.xczx.messagesdk.model.po.MqMessage;
import com.wjl.xczx.messagesdk.service.MessageProcessAbstract;
import com.wjl.xczx.messagesdk.service.MqMessageService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/19
 * @description
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CoursePublicTask extends MessageProcessAbstract {


    @XxlJob("coursePublish")
    public void taskPublic(){
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();
        this.process(shardIndex,shardTotal,"course_publish",30,60);
    }

    private final CoursePublishService publishService;

    /**
     *  任务幂等性问题 采用字段状态
     * @param mqMessage 执行任务内容
     * @return
     */
    @Override
    public boolean execute(MqMessage mqMessage) {
        String courseId = mqMessage.getBusinessKey1();
        long parseLong = Long.parseLong(courseId);
        generateCourseHtml(mqMessage, parseLong);

        return true;
    }

    /**
     *  阶段一 生成静态化页面
     * @param mqMessage
     * @param courseId
     */
    private void generateCourseHtml(MqMessage mqMessage, Long courseId){
        Long taskId = mqMessage.getId();
        int stageOne = this.getMqMessageService().getStageOne(taskId);
        if (stageOne > 0){
            log.debug("课程静态化任务完成，无需处理");
            return;
        }

        log.debug("开始课程界面静态化");
        File file = publishService.generateCourseHtml(courseId);
        publishService.uploadCourseHtml(courseId,file);


        // 标记为完成
        this.getMqMessageService().completedStageOne(taskId);
    }


    /**
     *  阶段二 存放索引库
     * @param mqMessage
     * @param courseId
     */
    private void saveCourseIndex(MqMessage mqMessage, Long courseId){
        Long taskId = mqMessage.getId();
        int stageTwo = this.getMqMessageService().getStageTwo(taskId);
        if (stageTwo > 0){
            log.debug("添加索引库任务完成，无需处理");
            return;
        }

        log.debug("开始课程界面静态化");

        // 标记为完成
        this.getMqMessageService().completedStageTwo(taskId);
    }

    private void saveCache(MqMessage mqMessage, Long courseId){
        Long taskId = mqMessage.getId();
        MqMessageService messageService = this.getMqMessageService();
        int stageThree = messageService.getStageThree(taskId);
        if (stageThree > 0){
            log.debug("添加缓存任务完成，无需处理");
            return;
        }

        log.debug("开始课程缓存");

        messageService.completedStageThree(taskId);
    }
}
