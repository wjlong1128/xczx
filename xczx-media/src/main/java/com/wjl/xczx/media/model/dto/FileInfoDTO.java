package com.wjl.xczx.media.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/16
 * @description 上传文件后 封装的信息对象
 */
@Accessors(chain = true)
@Data
public class FileInfoDTO {
    private String id;

    /**
     *  原始文件名
     */
    private String filename;
    private String bucket;

    private String fileType;
    private String contentType;
    private String filePath;

    private String fileId;

    private String url;

    private Long fileSize;

   // private String tags;
}
