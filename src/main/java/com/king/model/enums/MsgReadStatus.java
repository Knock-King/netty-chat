package com.king.model.enums;

/**
 * @author King
 * @version 1.0
 * @description 消息读状态 1、已读 2、未读
 * @date 2024/10/9 14:57
 */
public enum MsgReadStatus {
    /**
     * 1、已读
     */
    STATUS_1(1, "已读"),
    /**
     * 2、未读
     */
    STATUS_2(2, "未读");
    private Integer code;
    private String msg;

    private MsgReadStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsgName(Integer code) {
        if (code == null) {
            return null;
        }
        for (MsgReadStatus o : MsgReadStatus.values()) {
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
