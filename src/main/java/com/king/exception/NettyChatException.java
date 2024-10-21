package com.king.exception;

import com.king.model.enums.CommonError;

/**
 * @author King
 * @version 1.0
 * @description 聊天系统自定义异常处理器
 * @date 2024/10/21 15:44
 */
public class NettyChatException extends RuntimeException {

    private static final long serialVersionUID = 5565760508056698922L;

    private String msg;

    public NettyChatException() {
        super();
    }

    public NettyChatException(String errMessage) {
        super(errMessage);
        this.msg = errMessage;
    }

    public static void cast(CommonError commonError) {
        throw new NettyChatException(commonError.getErrMessage());
    }

    public static void cast(String errMessage) {
        throw new NettyChatException(errMessage);
    }

    public String getMsg() {
        return msg;
    }

}
