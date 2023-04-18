package com.wjl.xczx.media.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/16
 * @description
 */
@Data
@TableName("media_files")
public class MediaFile implements Serializable {

    //[{"code":"001001","desc":"图片"},{"code":"001002","desc":"视频"},{"code":"001003","desc":"其它"}]
    public static final String VIDEO = "001002";
    public static final String IMG = "001001";
    public static final String OTHER = "001003";

    /**
     *  文件id md5值
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     *  机构id
     */
    private Long companyId;

    /**
     *  机构名称
     */
    private String companyName;

    /**
     *  文件名称
     */
    private String filename;

    /**
     * 文件类型
     */
    private String fileType;
    /**
     *  标签
     */
    private String tags;

    /**
     *  文件桶
     */
    private String bucket;

    /**
     *  文件路径
     */
    private String filePath;

    /**
     * 文件id
     */
    private String fileId;

    /**
     * 访问地址
     */
    private String url;

    /**
     *  上传人
     */
    private String username;

    /**
     * 上传时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     *  修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime changeDate;

    /**
     * status 1 正常 0 不显示
     */
    private String status;

    /**
     *  备注
     */
    private String remark;

    /**
     *  审核状态
     */
    private String auditStatus;

    /**
     *  审核意见
     */
    private String auditMind;

    /**
     *  文件大小
     */
    private Long fileSize;


}
