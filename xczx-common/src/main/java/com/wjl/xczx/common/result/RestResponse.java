package com.wjl.xczx.common.result;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/18
 * @description
 */

import java.util.Objects;

public class RestResponse<T> {

    /**
     * 响应编码,0为正常,-1错误
     */
    private int code;

    /**
     * 响应提示信息
     */
    private String msg;

    /**
     * 响应内容
     */
    private T result;


    public RestResponse() {
        this(0, "success");
    }

    public RestResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 错误信息的封装
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> RestResponse<T> validfail(String msg) {
        RestResponse<T> response = new RestResponse<T>();
        response.setCode(-1);
        response.setMsg(msg);
        return response;
    }
    public static <T> RestResponse<T> validfail(T result,String msg) {
        RestResponse<T> response = new RestResponse<T>();
        response.setCode(-1);
        response.setResult(result);
        response.setMsg(msg);
        return response;
    }



    /**
     * 添加正常响应数据（包含响应内容）
     *
     * @return RestResponse Rest服务封装相应数据
     */
    public static <T> RestResponse<T> success(T result) {
        RestResponse<T> response = new RestResponse<T>();
        response.setResult(result);
        return response;
    }
    public static <T> RestResponse<T> success(T result,String msg) {
        RestResponse<T> response = new RestResponse<T>();
        response.setResult(result);
        response.setMsg(msg);
        return response;
    }

    /**
     * 添加正常响应数据（不包含响应内容）
     *
     * @return RestResponse Rest服务封装相应数据
     */
    public static <T> RestResponse<T> success() {
        return new RestResponse<T>();
    }


    public Boolean isSuccessful() {
        return this.code == 0;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestResponse<?> that = (RestResponse<?>) o;
        return code == that.code && Objects.equals(msg, that.msg) && Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, msg, result);
    }

    @Override
    public String toString() {
        return "RestResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}