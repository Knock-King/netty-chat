package com.king.model.enums;

/**
 * @author King
 * @version 1.0
 * @description 通用错误信息
 * @date 2024/10/21 15:44
 */
public enum CommonError {
    UNKNOWN_ERROR("执行过程异常，请重试。"),
    PARAMS_ERROR("非法参数"),
    OBJECT_NULL("对象为空"),
    QUERY_NULL("查询结果为空"),
    REQUEST_NULL("请求参数为空"),
    EMAIL_SEND_ERROR("邮件发送异常");
    private String errMessage;

    private CommonError(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }
}
