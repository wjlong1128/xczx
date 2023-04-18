package com.wjl.xczx.media.exception;

import com.wjl.xczx.common.State;
import com.wjl.xczx.common.exception.CommonException;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/16
 * @description
 */
public class MediaException extends CommonException {
    public MediaException(int code, String message) {
        super(code, message);
    }

    public MediaException(State status) {
        super(status);
    }

    public MediaException(Throwable cause, State status) {
        super(cause, status);
    }

    public MediaException(Throwable cause, int code, String message) {
        super(cause, code, message);
    }
}
