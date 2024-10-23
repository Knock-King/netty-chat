package com.king.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author King
 * @version 1.0
 * @description 朋友申请表
 * @date 2024/10/9 15:56
 */
@Data
public class ChatFriendRequestLogs implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long requestUserId;
    /**
     * 请求状态     1、通过     2、已拒绝   3、申请中
     */
    private Integer requestStatus;
    private LocalDateTime requestUpdateTime;
    /**
     * 查看状态      1、已查看   2、未查看
     */
    private Integer lookStatus;
    /**
     * 查看时间
     */
    private LocalDateTime lookUpdateTime;
    private String requestRemark;
    private LocalDateTime createTime;
    private Long createUserId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 数据状态   1、正常     2、申请者删除 3、被申请者删除
     */
    private Integer dataStatus;
}
