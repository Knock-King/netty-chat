package com.king.service.impl;

import com.alibaba.fastjson.JSON;
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
import org.springframework.util.ObjectUtils;

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

    @Override
    public void cleanMsgs(Long userId) {
        List<ChatFriendMsgLogs> friendMsgLogs = chatFriendMsgLogsMapper.getMsgLogsByOneUserId(userId);
        friendMsgLogs.forEach(msgLogs -> {
            cleanMsgMethod(userId, msgLogs);
        });
    }

    @Override
    public void cleanMsg(Long msgId, Long userId) {
        ChatFriendMsgLogs msgLogs = getById(msgId);
        cleanMsgMethod(userId, msgLogs);
    }

    private void cleanMsgMethod(Long userId, ChatFriendMsgLogs msgLogs) {
        String receiveId = msgLogs.getReceiveId();
        String sendId = msgLogs.getSendId();
        if (receiveId.equals(userId) || sendId.equals(userId)) {
            //该用户是者这条消息拥有者之一
            String cleanMsgUserIds = msgLogs.getCleanMsgUserIds();
            if (ObjectUtils.isEmpty(cleanMsgUserIds)) {
                //两人都未清空
                Long[] ids = new Long[1];
                ids[0] = userId;
                String userIds = JSON.toJSONString(ids);
                msgLogs.setCleanMsgUserIds(userIds);
            } else {
                //有一人清空
                Long[] ids = JSON.parseObject(cleanMsgUserIds, Long[].class);
                Long id = ids[0];
                Long[] cleanIds = new Long[2];
                cleanIds[0] = id;
                cleanIds[1] = userId;
                String userIds = JSON.toJSONString(cleanIds);
                msgLogs.setCleanMsgUserIds(userIds);
            }
            updateById(msgLogs);
        }
    }
}
