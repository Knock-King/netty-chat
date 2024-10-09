package com.king.model.enums;

/**
 * @author King
 * @version 1.0
 * @description 数据来源： 1000、电脑网页 （默认）
 * @date 2024/10/9 14:54
 */
public enum DataSources {

    /**
     * 1000、电脑网页 （默认）
     */
    SOURCES_TO_COMPUTER(1000, "电脑网页");

    private Integer code;
    private String msg;

    private DataSources(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsgName(Integer code) {
        if (code == null) {
            return null;
        }
        for (DataSources o : DataSources.values()) {
            if (o.getCode().equals(code)) {
                return o.msg;
            }
        }
        return null;
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
