package com.king.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author King
 * @version 1.0
 * @description 朋友信息表
 * @date 2024/10/9 18:16
 */
@Data
public class ChatFriendInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long userId;
    /**
     * 朋友用户id
     */
    private Long friendUserId;
    /**
     * 朋友备注
     */
    private String friendRemark;
    private LocalDateTime createTime;
    private Long createUserId;
    private LocalDateTime updateTime;
    private Long updateUserId;
    /**
     * 在线   离线
     */
    private String remark;
    /**
     * 数据状态   1、正常     2、删除 自己--->朋友
     */
    private Integer dataStatus;
    /**
     * 朋友数据状态   1、正常     2、删除 朋友--->自己
     */
    private Integer friendStatus;
}
