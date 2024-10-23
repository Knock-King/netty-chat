package com.king.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.king.model.dto.ChatFriendRequestLogsDto;
import com.king.model.entity.ChatMsg;
import com.king.model.po.ChatFriendRequestLogs;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author king
 * @since 2024-10-10
 */
public interface ChatFriendRequestLogsService extends IService<ChatFriendRequestLogs> {
    /**
     * @param chatMsg
     * @return void
     * @description 新增好友, 发送好友申请
     * @author King
     * @date 2024/10/11 13:49
     */
    void friendApplication(ChatMsg chatMsg);

    /**
     * @param userId
     * @param status 1:申请列表 2：被申请列表
     * @return java.util.List<com.king.model.po.ChatFriendRequestLogs>
     * @description 获取好友申请列表
     * @author King
     * @date 2024/10/23 14:12
     */
    List<ChatFriendRequestLogsDto> getFriendApplication(Long userId,Integer status);

    /**
     * @param userId
     * @param friendId
     * @return void
     * @description 查看好友申请
     * @author King
     * @date 2024/10/23 14:20
     */
    void lookFriendApplication(Long userId, Long friendId);

    /**
    * @description 审批好友申请
    * @param userId
     * @param friendId
     * @param requestStatus 审批状态
    * @return java.lang.Boolean
    * @author King
    * @date 2024/10/23 14:24
    */
    Boolean approvalFriendApplication(Long userId, Long friendId, Integer requestStatus);

    /**
    * @description 删除申请记录
    * @param userId
     * @param friendId
     * @param status 1:申请者删除申请记录 2：被申请者删除申请记录
    * @return java.lang.Boolean
    * @author King
    * @date 2024/10/23 14:34
    */
    Boolean deleteFriendApplication(Long userId, Long friendId, Integer status);
}
