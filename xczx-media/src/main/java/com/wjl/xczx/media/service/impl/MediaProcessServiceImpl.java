package com.wjl.xczx.media.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjl.xczx.media.mapper.MediaProcessMapper;
import com.wjl.xczx.media.model.entity.MediaFile;
import com.wjl.xczx.media.model.entity.MediaProcess;
import com.wjl.xczx.media.model.entity.MediaProcessHistory;
import com.wjl.xczx.media.service.MediaFileService;
import com.wjl.xczx.media.service.MediaProcessHistoryService;
import com.wjl.xczx.media.service.MediaProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/17
 * @description
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MediaProcessServiceImpl extends ServiceImpl<MediaProcessMapper, MediaProcess> implements MediaProcessService {

    private final MediaFileService mediaFileService;

    private final MediaProcessHistoryService mediaProcessHistoryService;
    @Override
    public List<MediaProcess> getPendingTasks(int shardIndex, int shardTotal, int count) {
        return this.baseMapper.getPendingTasks(shardIndex,shardTotal,count);
    }

    @Override
    public boolean startTask(Long id) {
        return this.baseMapper.startTask(id) > 0;
    }

    @Transactional
    @Override
    public void processVideoStatus(long taskId, String fileId, String status, String filePath, String url, String message) {
        MediaProcess process = getById(taskId);
        if (process == null){
            return;
        }

        // 如果更新状态失败 那么更新状态更改为失败 添加一次失败计数
        if (MediaProcess.PROCESSING_FAILED.equals(status)){
            process.setFailCount(process.getFailCount() + 1);
            process.setErrormsg(message);
            process.setStatus(MediaProcess.PROCESSING_FAILED);
            log.info("视频文件转码失败 fileId:{}, filename:{}, message:{}",process.getFileId(),process.getFilePath(),message);
            this.updateById(process);
            return;
        }

        // 成功 更新文件表的信息, 更新处理表的信息, 更新历史表 删除处理表
        if (MediaProcess.PROCESSING_SUCCEEDED.equals(status)){
            MediaFile mediaFile = mediaFileService.getById(fileId);
            String oldFilePath = mediaFile.getFilePath();
            mediaFile.setUrl(url);
            mediaFile.setFilePath(filePath);
            mediaFileService.updateById(mediaFile);

            process.setFilePath(filePath);
            process.setUrl(url);
            process.setStatus(MediaProcess.PROCESSING_SUCCEEDED);
            process.setFinishDate(LocalDateTime.now());
            updateById(process);

            process = getById(process.getId());
            MediaProcessHistory processHistory = new MediaProcessHistory();
            // 只有一个字段不一样，并且id mybatisplus 自增
            BeanUtils.copyProperties(process,processHistory);
            processHistory.setOldFilePath(oldFilePath);
            mediaProcessHistoryService.save(processHistory);
            // 删除当前任务
            this.removeById(process.getId());
            return;
        }
    }

}
