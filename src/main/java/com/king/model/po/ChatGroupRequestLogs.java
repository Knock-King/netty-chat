package com.king.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author King
 * @version 1.0
 * @description 申请入群表
 * @date 2024/10/11 17:42
 */
@Data
public class ChatGroupRequestLogs {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    //加入群聊id
    private Long chatGroupInfoId;
    //申请人
    private Long requestUserId;
    //审批人
    private Long approvalUserId;
    //请求状态     1、通过     2、已拒绝   3、申请中
    private Integer requestStatus;
    //审批时间
    private LocalDateTime requestUpdateTime;
    //查看状态      1、已查看   2、未查看
    private Integer lookStatus;
    //查看时间
    private LocalDateTime lookUpdateTime;
    //为啥入群
    private String requestRemark;
    //申请时间
    private LocalDateTime createTime;
}
