package com.wjl.xczx.media.model.dto;

import lombok.Data;

import java.io.Serializable;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/16
 * @description
 */
@Data
public class QueryMediaParamsDTO implements Serializable {

    private String filename;

    private String fileType;

    private String auditStatus;

}
