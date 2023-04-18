package com.wjl.xczx.content.course.exception;

import com.wjl.xczx.common.exception.CommonException;
import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.common.result.status.StateEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/15
 * @description
 */
@Slf4j
@RestControllerAdvice
public class CourseExceptionHandler {

    @ExceptionHandler(CourseException.class)
    public Result courseExceptionHandler(CourseException e) {
        String message = e.getMessage();
        log.error("course 异常: {}, e:", message, e);
        return Result.error(e.getCode(), message);
    }

    @ExceptionHandler(CommonException.class)
    public Result commonExceptionHandler(CommonException e) {
        String message = e.getMessage();
        log.error("系统 异常: {}, e:", message, e);
        return Result.error(e.getCode(), message);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));
        log.error("参数校验 异常: {}, e: {}", message, e);
        return Result.error(StateEnum.REQUEST_PARAMETERS_ERROR.getCode(), message);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e) {
        log.error("系统 异常: {}, e: {}, type: {}:", e.getMessage(), e, e.getClass());
        return Result.error(StateEnum.UN_KNOW_EX.getCode(), StateEnum.UN_KNOW_EX.getMessage());
    }


}
