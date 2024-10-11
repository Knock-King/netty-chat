package com.king.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author King
 * @version 1.0
 * @description 群信息表
 * @date 2024/10/11 14:34
 */
@Data
public class ChatGroupInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    //群名称
    private String groupName;
    //群账号
    private Long groupId;
    //创建时间
    private LocalDateTime createTime;
    //创建人id
    private Long createUserId;
    //群介绍
    private String remark;
    //群类型
    private Integer groupType;
    //群人数
    private Integer groupNumber;
    //群限制人数
    private Integer groupLimitNumber;
    //群头像id
    private Long groupAvatarId;
    //如何加入（1：随便加入 2：需要管理员或者群主同意 3：输入验证密码）
    private Integer joinType;
}
