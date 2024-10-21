package com.king.model.entity;

import com.king.model.enums.HttpCode;
import lombok.Data;
import lombok.ToString;

/**
 * @author King
 * @version 1.0
 * @description 通用结果类型
 * @date 2024/10/21 15:44
 */
@Data
@ToString
public class RestResponse<T> {
    /**
     * 响应编码
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

    /**
     * @description 基础体
     * @author King
     * @date 2023/11/9 21:28
     */
    public RestResponse() {
        this(HttpCode.SUCCESS.getCode(), HttpCode.SUCCESS.getMsg());
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
    public static <T> RestResponse<T> fail(String msg) {
        RestResponse<T> response = new RestResponse<T>();
        response.setCode(HttpCode.FAILURE.getCode());
        response.setMsg(msg);
        return response;
    }

    public static <T> RestResponse<T> fail(T result) {
        RestResponse<T> response = new RestResponse<T>();
        response.setCode(HttpCode.FAILURE.getCode());
        response.setResult(result);
        return response;
    }

    /**
     * 添加正常响应数据（包含响应内容）
     *
     * @return RestResponse Rest服务封装相应数据
     */
    public static <T> RestResponse<T> success(T result) {
        RestResponse<T> response = new RestResponse<T>();
        response.setCode(HttpCode.SUCCESS.getCode());
        response.setMsg(HttpCode.SUCCESS.getMsg());
        response.setResult(result);
        return response;
    }

    public static <T> RestResponse<T> success(T result, String msg) {
        RestResponse<T> response = new RestResponse<T>();
        response.setCode(HttpCode.SUCCESS.getCode());
        response.setMsg(msg);
        response.setResult(result);
        return response;
    }
}
