package com.king.model.enums;

/**
 * @author King
 * @version 1.0
 * @description 聊天信息版本 1.0.0 开始
 * @date 2024/10/9 14:54
 */
public enum ChatVersion {
    /**
     * 聊天信息版本	1.0.0
     */
    VSERSION("1.0.0", "版本1.0.0");
    private String code;
    private String msg;

    private ChatVersion(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsgName(String code) {
        if (code == null) {
            return null;
        }
        for (ChatVersion o : ChatVersion.values()) {
            if (o.getCode().equals(code)) {
                return o.msg;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
