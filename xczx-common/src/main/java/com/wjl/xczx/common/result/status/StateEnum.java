package com.wjl.xczx.common.result.status;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @description
 * @date 2023/4/14
 */

import com.wjl.xczx.common.State;

public enum StateEnum implements State {
    SUCCESS(200,"success"),
    UN_KNOW_EX(500,"系统出问题了，请稍后再试"),
    REQUEST_PARAMETERS_ERROR(400,"参数出错"),
    ;
    private int code;
    private String message;

    StateEnum(int code, String message) {
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
