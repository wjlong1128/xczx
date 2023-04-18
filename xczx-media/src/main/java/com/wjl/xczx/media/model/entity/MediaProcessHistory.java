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
@TableName("media_process_history")
public class MediaProcessHistory implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String fileId; // 文件标识

    private String filename; // 文件名称

    private String bucket ;

    private  String filePath;

    private String status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;
    private LocalDateTime finishDate;
    private String url;
    private Integer failCount; // 失败次数

    private String oldFilePath;
    private String errormsg; // 失败原因
}
