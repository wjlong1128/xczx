package com.wjl.xczx.media.exception.state;

import com.wjl.xczx.common.State;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/16
 * @description
 */
public enum MediaStateEnum implements State {
    FILE_NAME_ERROR(41001, "文件名错误"),
    COMPANY_ID_NULL_ERROR(41002, "机构id不能为空"),
    FILE_UPLOAD_ERROR(41003,"文件上传失败" ),
    FILE_NOT_FOUNT(41004,"文件不存在" ),
    FILE_SAVE_ERROR(41005,"文件入库失败"),
    CRT_TEMP_ERROR(41006,"创建临时文件失败"),
    FILE_ACCESS_ERROR(41007,"文件访问失败,请稍后再试" ),
    FILE_ERROR(41008,"系统异常,请稍后再试" ),
    MERGE_FILE_ERROR(41009,"文件合并失败" ),
    CHUNK_FILE_CLEAR_ERROR(41010,"清理分块文件失败")
    ;
    private int code;
    private String message;

    MediaStateEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
