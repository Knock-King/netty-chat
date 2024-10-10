package com.king.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.king.model.po.ChatFriendMsgLogs;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author king
 * @since 2024-10-10
 */
public interface ChatFriendMsgLogsService extends IService<ChatFriendMsgLogs> {
    /**
    * @description 添加消息
    * @param chatFriendMsgLogs
    * @return com.king.model.po.ChatFriendMsgLogs
    * @author King
    * @date 2024/10/10 10:30
    */
    ChatFriendMsgLogs saveMsgLogs(ChatFriendMsgLogs chatFriendMsgLogs);

    /**
    * @description 修改消息为离线
    * @param msgId
    * @return void
    * @author King
    * @date 2024/10/10 10:53
    */
    void updateOfflineStatus(Long msgId);
}
