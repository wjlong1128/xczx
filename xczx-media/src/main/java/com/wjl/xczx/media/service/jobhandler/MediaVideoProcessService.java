package com.wjl.xczx.media.service.jobhandler;

import com.wjl.xczx.media.config.minio.MinIoProperties;
import com.wjl.xczx.media.exception.MediaException;
import com.wjl.xczx.media.model.entity.MediaProcess;
import com.wjl.xczx.media.service.MediaProcessService;
import com.wjl.xczx.media.service.MinioService;
import com.wjl.xczx.media.utils.MediaFileUtils;
import com.wjl.xczx.media.utils.Mp4VideoUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/17
 * @description
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MediaVideoProcessService {

    private final MediaProcessService processService;
    private final MinioService minioService;
    private final MinIoProperties properties;
    @Value("${media.ffmpeg.path}")
    private String ffmpegPath;

    /**
     * 2、分片广播任务
     */
    @XxlJob("mediaProcessHandler")
    public void mediaProcessHandler() throws Exception {
        int shardIndex = XxlJobHelper.getShardIndex() ;
        int shareTotal = XxlJobHelper.getShardTotal();
        // 获取任务的数量为当前cpu核心数
        int taskNum = Runtime.getRuntime().availableProcessors();
        // 查询当前该执行的任务
        List<MediaProcess> tasks = processService.getPendingTasks(shardIndex, shareTotal, taskNum);
        log.debug("当前执行器[{}]获取的任务数量:{}", shardIndex, tasks.size());
        taskNum = tasks.size();
        if (CollectionUtils.isEmpty(tasks)) {
            return;
        }

        // 创建对应任务的线程池
        ExecutorService pool = Executors.newFixedThreadPool(taskNum);
        CountDownLatch latch = new CountDownLatch(taskNum);
        for (MediaProcess task : tasks) {
            pool.execute(() -> {
                taskProcess(latch, task);
            });
        }

        latch.await(30, TimeUnit.MINUTES);
        pool.shutdown(); // 关闭线程池防止 资源浪费
    }

    // 人麻了...
    private void taskProcess(CountDownLatch latch, MediaProcess task) {
        Long taskId = task.getId();
        String fileId = task.getFileId();
        try {
            boolean startTask = processService.startTask(taskId);
            if (!startTask) {
                log.debug("抢占任务失败 taskId:{}", task);
                return;
            }
            // 执行转码
            String bucket = properties.getBucket().getVideofiles();
            String objectName = task.getFilePath();
            File downloadFile = null;

            try {
                downloadFile = minioService.downloadFile(bucket, objectName);
            } catch (MediaException e) {
                log.warn("获取原始文件失败 taskId:{}, bucket:{}, objectName:{}, errMessage:{}, error:{}", taskId, bucket, objectName, e.getMessage(), e);
                processService.processVideoStatus(taskId, fileId,MediaProcess.PROCESSING_FAILED,null,null,"获取原始文件失败");
                return;
            }

            String md5 = fileId;
            String mp4Name = md5 + ".mp4";

            // 创建临时文件 作为转码后的存放地址
            File tempFile = null;
            try {
                tempFile = File.createTempFile(md5, mp4Name);
            } catch (IOException e) {
                log.warn("创建临时文件失败 taskId:{}, bucket:{}, objectName:{}, errMessage:{}, error:{}", taskId, bucket, objectName, e.getMessage(), e);
                processService.processVideoStatus(taskId,fileId,MediaProcess.PROCESSING_FAILED,null,null,"创建临时文件失败");
                return;
            }
            String localPath = downloadFile.getAbsolutePath();
            String mp4Path = tempFile.getAbsolutePath();
            // 转码
            Mp4VideoUtil util = new Mp4VideoUtil(ffmpegPath, localPath, mp4Name, mp4Path);
            String result = util.generateMp4();
            // 转码失败
            if (! "success".equals(result)){
                log.warn("视频转码失败 taskId:{}, result:{}, bucket:{}, objectName:{}",taskId,result,bucket,objectName);
                processService.processVideoStatus(taskId,fileId,null,null,null,result);
                return;
            }

            String newObjectName = MediaFileUtils.getDatePathFileName(mp4Name);
            // 上传文件
            try {
                minioService.uploadFile(bucket, "video/mp4",mp4Path,newObjectName);
            } catch (MediaException e) {
                log.warn("转码后文件上传失败 taskId:{}, bucket:{}, objectName:{}, errMessage:{}, e:{}",task,bucket,newObjectName,e.getMessage(),e);
                processService.processVideoStatus(taskId,fileId,null,null,null,"转码后文件上传失败");
                return;
            }
            // 成功
            String url = "/" + bucket + "/" + newObjectName;
            processService.processVideoStatus(taskId,fileId,MediaProcess.PROCESSING_SUCCEEDED,newObjectName,url,"success");
            log.info("文件转码上传成功 taskId:{}, bucket:{}, objectName:{}",taskId,bucket,newObjectName);
        } finally {
            latch.countDown();
        }
    }

}
