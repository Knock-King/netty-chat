package com.king.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.king.mapper.ChatFriendRequestLogsMapper;
import com.king.model.entity.ChatMsg;
import com.king.model.po.ChatFriendRequestLogs;
import com.king.service.ChatFriendRequestLogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author king
 */
@Slf4j
@Service
public class ChatFriendRequestLogsServiceImpl extends ServiceImpl<ChatFriendRequestLogsMapper, ChatFriendRequestLogs> implements ChatFriendRequestLogsService {
    @Override
    public void AddFriend(ChatMsg chatMsg) {
        String receiveId = chatMsg.getReceiveId();
        String sendId = chatMsg.getSendId();
        String msgContent = chatMsg.getMsgContent();
        ChatFriendRequestLogs chatFriendRequestLogs = new ChatFriendRequestLogs();
        chatFriendRequestLogs.setUserId(Long.valueOf(sendId));
        chatFriendRequestLogs.setRequestUserId(Long.valueOf(receiveId));
        chatFriendRequestLogs.setRequestStatus(3);
        chatFriendRequestLogs.setLookStatus(2);
        chatFriendRequestLogs.setRequestRemark(msgContent);
        chatFriendRequestLogs.setCreateTime(LocalDateTime.now());
        chatFriendRequestLogs.setDataStatus(1);
        save(chatFriendRequestLogs);
    }
}
