package com.king.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author King
 * @version 1.0
 * @description 群内人员信息
 * @date 2024/10/11 14:45
 */
@Data
public class ChatGroupUserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    //群聊id
    private Long chatGroupInfoId;
    //群用户id
    private Long groupUserId;
    //身份类型（1：群主 2：管理员 3：普通群员）
    private Integer identityType;
    //群昵称
    private String groupNickname;
    //群人员昵称
    private String groupUserNickname;
    //入群时间
    private LocalDateTime joinGroupTime;
    //是否禁言(1:正常 0：禁言)
    private Integer silence;
    //禁言时长
    private String silenceTime;
    //禁言开始时间
    private LocalDateTime silenceStartTime;
    //状态：1:在线 0:离线
    private Integer status;
}
