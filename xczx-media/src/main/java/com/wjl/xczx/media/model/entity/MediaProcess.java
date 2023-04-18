package com.wjl.xczx.media.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/17
 * @description
 */
@Data
@TableName("media_process")
public class MediaProcess implements Serializable {
    public static final String NOT_PROCESSED = "1";
    public static final String PROCESSING_SUCCEEDED = "2";
    public static final String PROCESSING_FAILED = "3";
    @TableId(type = IdType.AUTO)
    private Long id;

    private String fileId; // 文件标识

    private String filename; // 文件名称

    private String bucket ;

    private  String filePath;

    private String status;

    private Integer failCount;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;
    private LocalDateTime finishDate;
    private String url;
    private String errormsg;



}
