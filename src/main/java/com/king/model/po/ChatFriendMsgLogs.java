package com.king.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author King
 * @version 1.0
 * @description 聊天记录表
 * @date 2024/10/9 15:53
 */
@Data
public class ChatFriendMsgLogs implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户id  消息属于谁的
     */
    private Long userId;
    /**
     * 朋友信息表id
     */
    private Long friendInfoId;
    /**
     * 发送者id
     */
    private String sendId;
    /**
     * 发送时间
     */
    private LocalDateTime sendTime;
    /**
     * 接受者id
     */
    private String receiveId;
    /**
     * 读取时间
     */
    private LocalDateTime receiveTime;
    /**
     * 消息类型
     */
    private Integer msgType;
    /**
     * 消息内容
     */
    private String msgContent;
    /**
     * 消息读状态    1、已读    ，  2、未读
     */
    private Integer msgReadStatus;
    /**
     * 离线消息    1、在线  ， 2、离线
     */
    private Integer msgOfflineStatus;
    private LocalDateTime createTime;
    /**
     * 聊天信息版本 1.0.0 开始
     */
    private String chatVersion;
    /**
     * 数据来源：  1000、电脑网页 （默认）
     */
    private Integer dataSources;
    /**
     * 发送者ip地址
     */
    private String ipSendLocation;
    /**
     * 接受者ip地址
     */
    private String ipReceiveLocation;
    /**
     * 数据状态   1、正常     2、删除
     */
    private Integer dataStatus;
}
