package com.wjl.xczx.common.exception;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @description
 * @date 2023/4/14
 */

import com.wjl.xczx.common.State;

public class CommonException extends RuntimeException implements State {

    private int code;
    private String message;

    public CommonException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public CommonException(State status) {
        this(status.getCode(), status.getMessage());
    }

    public CommonException(Throwable cause, State status) {
        this(cause, status.getCode(), status.getMessage());
    }

    public CommonException(Throwable cause, int code, String message) {
        super(message, cause);
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
