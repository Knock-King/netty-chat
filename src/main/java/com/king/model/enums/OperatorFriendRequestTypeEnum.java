package com.king.model.enums;

/**
 * @author King
 * @version 1.0
 * @description 忽略或者通过 好友请求的枚举
 * @date 2024/10/9 14:57
 */
public enum OperatorFriendRequestTypeEnum {
    IGNORE(0, "忽略"),
    PASS(1, "通过");
    public final Integer type;
    public final String msg;

    OperatorFriendRequestTypeEnum(Integer type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public static String getMsgByType(Integer type) {
        for (OperatorFriendRequestTypeEnum operType : OperatorFriendRequestTypeEnum.values()) {
            if (operType.getType() == type) {
                return operType.msg;
            }
        }
        return null;
    }

    public Integer getType() {
        return type;
    }
}
