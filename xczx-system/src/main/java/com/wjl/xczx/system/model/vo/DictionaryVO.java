package com.wjl.xczx.system.model.vo;

import lombok.Data;

import java.io.Serializable;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
@Data
public class DictionaryVO implements Serializable {
    private Long id;

    /**
     * 数据字典名称
     */
    private String name;

    /**
     * 数据字典代码
     */
    private String code;

    /**
     * 数据字典项--json格式
     [{
     "sd_name": "低级",
     "sd_id": "200001",
     "sd_status": "1"
     }, {
     "sd_name": "中级",
     "sd_id": "200002",
     "sd_status": "1"
     }, {
     "sd_name": "高级",
     "sd_id": "200003",
     "sd_status": "1"
     }]
     */
    private String itemValues;
}
