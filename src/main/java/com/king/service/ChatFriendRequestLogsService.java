package com.king.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.king.model.entity.ChatMsg;
import com.king.model.po.ChatFriendRequestLogs;

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
    * @description 新增好友,发送好友申请
    * @param chatMsg
    * @return void
    * @author King
    * @date 2024/10/11 13:49
    */
    void AddFriend(ChatMsg chatMsg);
}
