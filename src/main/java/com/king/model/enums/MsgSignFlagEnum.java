package com.king.model.enums;

/**
 * @author King
 * @version 1.0
 * @description 消息签收状态
 * @date 2024/10/9 14:57
 */
public enum MsgSignFlagEnum {
    unsign(0, "未签收"),
    signed(1, "已签收");
    public final Integer type;
    public final String content;

    MsgSignFlagEnum(Integer type, String content) {
        this.type = type;
        this.content = content;
    }

    public Integer getType() {
        return type;
    }
}
