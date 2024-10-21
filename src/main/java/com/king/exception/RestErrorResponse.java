package com.king.exception;

import com.king.model.enums.HttpCode;

import java.io.Serializable;

/**
 * @author King
 * @version 1.0
 * @description 错误响应参数包装 和前端返回的异常信息模型
 * @date 2024/19/21 15:52
 */
public class RestErrorResponse implements Serializable {
    private String msg;
    private Integer code;

    public RestErrorResponse(String errMessage) {
        this.code = HttpCode.FAILURE.getCode();
        this.msg = errMessage;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
