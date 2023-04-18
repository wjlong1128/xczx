package com.wjl.xczx.common.result;

import com.wjl.xczx.common.State;
import com.wjl.xczx.common.result.status.StateEnum;

import java.io.Serializable;
import java.util.Objects;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @description
 * @date 2023/4/14
 */

public class Result<T>  implements Serializable, State {

    private int code;
    private String message;
    private T data;

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(StateEnum.SUCCESS.getCode(), message, data);
    }

    public static <T> Result<T> success() {
        return success(null);
    }
    public static <T> Result<T> success(T data) {
        return new Result<>(StateEnum.SUCCESS.getCode(), StateEnum.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> okMessage(String message) {
        return new Result<>(StateEnum.SUCCESS.getCode(), message, null);
    }

    public static <T> Result<T> error(int code, String message, T data) {
        return new Result<>(code, message, data);
    }

    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }

    public static <T> Result<T> error(State status, T data) {
        return new Result<>(status.getCode(), status.getMessage(), data);
    }

    public static <T> Result<T> error(State status) {
        return new Result<>(status.getCode(), status.getMessage(), null);
    }


    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    @Override
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result<?> result = (Result<?>) o;
        return code == result.code && Objects.equals(message, result.message) && Objects.equals(data, result.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, data);
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
