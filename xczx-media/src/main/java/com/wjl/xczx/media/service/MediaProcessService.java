package com.wjl.xczx.media.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjl.xczx.media.model.entity.MediaProcess;

import java.util.List;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/17
 * @description
 */
public interface MediaProcessService extends IService<MediaProcess> {

    /**
     *  获取待处理的任务
     * @param shardIndex 当前执行器索引
     * @param shardTotal 分片执行器总数
     * @param count 获取任务数
     * @return
     */
    List<MediaProcess> getPendingTasks(int shardIndex,int shardTotal,int count);

    /**
     * 乐观锁
     * @param id
     * @return
     */
    boolean startTask(Long id);

    /**
     * 处理视频更新状态
     * @param taskId 任务id
     * @param fileId 文件id
     * @param status 更新的状态
     * @param url 更新的url
     * @param filePath 更新后的文件路径
     * @param message 成功或者失败的消息
     */
    void processVideoStatus(long taskId,String fileId,String status,String filePath,String url,String message);
}
