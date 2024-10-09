package com.king.model.enums;

/**
 * @author King
 * @version 1.0
 * @description 添加好友前置状态
 * @date 2024/10/9 14:57
 */
public enum SearchFriendsStatusEnum {
    SUCCESS(0, "发送好友申请成功"),
    USER_NOT_EXIST(1, "查无此用户"),
    NOT_YOURSELF(2, "不能添加你自己为好友"),
    ALREADY_FRIENDS(3, "该用户已经是你的好友");
    public final Integer status;
    public final String msg;

    SearchFriendsStatusEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static String getMsgByKey(Integer status) {
        for (SearchFriendsStatusEnum type : SearchFriendsStatusEnum.values()) {
            if (type.getStatus() == status) {
                return type.msg;
            }
        }
        return null;
    }

    public Integer getStatus() {
        return status;
    }
}
