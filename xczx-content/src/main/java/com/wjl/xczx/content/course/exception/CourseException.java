package com.wjl.xczx.content.course.exception;

import com.wjl.xczx.common.State;
import com.wjl.xczx.common.exception.CommonException;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
public class CourseException extends CommonException {
    public CourseException(int code, String message) {
        super(code, message);
    }

    public CourseException(Throwable cause, int code, String message) {
        super(cause, code, message);
    }

    public CourseException(State status) {
        super(status);
    }
}
