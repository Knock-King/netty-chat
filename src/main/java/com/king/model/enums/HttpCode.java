package com.king.model.enums;

/**
 * @author King
 * @version 1.0
 * @description http枚举
 * @date 2024/10/21 15:44
 */
public enum HttpCode {
    /**
     * 200,"成功"
     */
    SUCCESS(200, "succee"),
    /**
     * 119,"失败"
     */
    FAILURE(119, "failure"),
    /**
     * 120, "内部错误"
     */
    ERROR(120, "内部错误"),
    /**
     * 500,"系统繁忙"
     */
    EXCEPTIONS(500, "系统繁忙"),
    /**
     * 900,"参数验证失败"
     */
    PARAM_VERIFICATION(900, "参数验证失败"),
    /**
     * 990, "登录失败"
     */
    LOGIN_FAIL(990, "登录失败"),
    /**
     * 1000, "操作权限不足,请联系管理员"
     */
    NO_OPERATION_PERMISSION(1000, "操作权限不足,请联系管理员"),
    /**
     * 911, "登录已失效,请重新登录"
     */
    NO_LOGIN(911, "登录已失效,请重新登录"),
    /**
     * 999, "登录已失效,请重新登录"
     */
    TOKEN_EXPIRED(999, "token过期");
    private Integer code;
    private String msg;

    private HttpCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
