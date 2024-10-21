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

    /**
    * @description 批量签收消息
    * @param signIdList 签收id的list
    * @return void
    * @author King
    * @date 2024/10/21 16:57
    */
    void batchSignMsgs(List<String> signIdList);

    /**
    * @description 清空聊天消息
    * @param userId
    * @return void
    * @author King
    * @date 2024/10/21 17:07
    */
    void cleanMsgs(Long userId);

    void cleanMsg(Long msgId,Long userId);
}
