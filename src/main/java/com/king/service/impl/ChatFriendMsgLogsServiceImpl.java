package com.king.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.king.mapper.ChatFriendInfoMapper;
import com.king.mapper.ChatFriendMsgLogsMapper;
import com.king.model.enums.ChatVersion;
import com.king.model.enums.DataSources;
import com.king.model.enums.MsgReadStatus;
import com.king.model.po.ChatFriendInfo;
import com.king.model.po.ChatFriendMsgLogs;
import com.king.service.ChatFriendMsgLogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author king
 */
@Slf4j
@Service
public class ChatFriendMsgLogsServiceImpl extends ServiceImpl<ChatFriendMsgLogsMapper, ChatFriendMsgLogs> implements ChatFriendMsgLogsService {
    @Autowired
    private ChatFriendMsgLogsMapper chatFriendMsgLogsMapper;
    @Autowired
    private ChatFriendInfoMapper chatFriendInfoMapper;

    @Override
    public ChatFriendMsgLogs saveMsgLogs(ChatFriendMsgLogs logs) {
        //查找朋友信息
        ChatFriendInfo chatFriendInfo = chatFriendInfoMapper
                .selectOne(new LambdaQueryWrapper<ChatFriendInfo>()
                        .eq(ChatFriendInfo::getUserId, logs.getSendId())
                        .eq(ChatFriendInfo::getFriendUserId, logs.getReceiveId()));
        ChatFriendMsgLogs chatFriendMsgLogs = new ChatFriendMsgLogs();
        BeanUtils.copyProperties(logs, chatFriendMsgLogs);
        chatFriendMsgLogs.setCreateTime(LocalDateTime.now());
        chatFriendMsgLogs.setFriendInfoId(chatFriendInfo.getId());
        chatFriendMsgLogs.setUserId(Long.valueOf(logs.getSendId()));
        chatFriendMsgLogs.setSendTime(LocalDateTime.now());
        chatFriendMsgLogs.setMsgReadStatus(MsgReadStatus.STATUS_NO_READ.getCode());
        chatFriendMsgLogs.setChatVersion(ChatVersion.VSERSION.getCode());
        chatFriendMsgLogs.setDataSources(DataSources.SOURCES_TO_COMPUTER.getCode());
        chatFriendMsgLogs.setDataStatus(1);
        save(chatFriendMsgLogs);
        return chatFriendMsgLogs;
    }

    @Override
    public void updateOfflineStatus(Long msgId) {
        ChatFriendMsgLogs logs = new ChatFriendMsgLogs();
        logs.setId(msgId);
        logs.setMsgOfflineStatus(2);
        chatFriendMsgLogsMapper.updateById(logs);
    }

    @Override
    public void batchSignMsgs(List<String> signIdList) {
        List<ChatFriendMsgLogs> chatFriendMsgLogs = signIdList.stream().map(item -> {
            ChatFriendMsgLogs logs = new ChatFriendMsgLogs();
            logs.setId(Long.valueOf(item));
            logs.setMsgReadStatus(MsgReadStatus.STATUS_READ.getCode());
            logs.setReceiveTime(LocalDateTime.now());
            return logs;
        }).collect(Collectors.toList());
        saveOrUpdateBatch(chatFriendMsgLogs);
    }
}
