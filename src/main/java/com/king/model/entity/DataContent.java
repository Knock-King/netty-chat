package com.king.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author King
 * @version 1.0
 * @description 接收前端返回消息实体类
 * @date 2024/10/9 15:15
 */
@Data
public class DataContent implements Serializable {
    private static final long serialVersionUID = 8021381444738260454L;

    private Integer action;        // 动作类型

    private ChatMsg chatMsg;    // 用户的聊天内容entity

    private Integer msgType; //消息的类型

    private String extend;        // 扩展字段
}
