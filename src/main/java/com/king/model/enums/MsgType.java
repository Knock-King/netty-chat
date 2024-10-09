package com.king.model.enums;

/**
 * @author King
 * @version 1.0
 * @description 消息类型
 * @date 2024/10/9 14:57
 */
public enum MsgType {
    /**
     * 1 表示文本消息
     */
    TYPE_TEXT("1", "文本消息"),
    /**
     * 2 表示图片
     */
    TYPE_PICTURE("2", "表示图片"),
    /**
     * 3 表示语音
     */
    TYPE_VOICE("3", "表示语音"),
    /**
     * 4 表示视频
     */
    TYPE_VIDEO("4", "表示视频");
    private String code;
    private String msg;

    private MsgType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getChatVersionMsg(String code) {
        if (code == null) {
            return null;
        }
        for (MsgType o : MsgType.values()) {
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
