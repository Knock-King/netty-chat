package com.king.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.king.mapper.ChatFriendRequestLogsMapper;
import com.king.model.dto.ChatFriendRequestLogsDto;
import com.king.model.entity.ChatMsg;
import com.king.model.po.ChatFriendRequestLogs;
import com.king.model.po.UserInfo;
import com.king.service.ChatFriendInfoService;
import com.king.service.ChatFriendRequestLogsService;
import com.king.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
public class ChatFriendRequestLogsServiceImpl extends ServiceImpl<ChatFriendRequestLogsMapper, ChatFriendRequestLogs> implements ChatFriendRequestLogsService {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ChatFriendInfoService chatFriendInfoService;

    @Override
    public void friendApplication(ChatMsg chatMsg) {
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

    @Override
    public List<ChatFriendRequestLogsDto> getFriendApplication(Long userId, Integer status) {
        if (status != 1 && status != 2) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<ChatFriendRequestLogs> wrapper = new LambdaQueryWrapper<>();
        if (status == 1) {
            //1:申请列表
            wrapper.eq(ChatFriendRequestLogs::getUserId, userId);
            wrapper.ne(ChatFriendRequestLogs::getDataStatus, 2);
        } else {
            //2：被申请列表
            wrapper.eq(ChatFriendRequestLogs::getRequestUserId, userId);
            wrapper.ne(ChatFriendRequestLogs::getDataStatus, 3);
        }
        wrapper.orderByDesc(ChatFriendRequestLogs::getCreateTime);
        List<ChatFriendRequestLogs> list = list(wrapper);
        return list.stream().map(item -> {
            ChatFriendRequestLogsDto chatFriendRequestLogsDto = new ChatFriendRequestLogsDto();
            BeanUtils.copyProperties(item, chatFriendRequestLogsDto);
            Long id = status == 1 ? item.getRequestUserId() : item.getUserId();
            UserInfo userInfo = userInfoService.getById(id);
            chatFriendRequestLogsDto.setRequestUserNickname(userInfo.getNickname());
            return chatFriendRequestLogsDto;
        }).collect(Collectors.toList());
    }

    @Override
    public void lookFriendApplication(Long userId, Long friendId) {
        LambdaQueryWrapper<ChatFriendRequestLogs> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatFriendRequestLogs::getUserId, userId);
        wrapper.eq(ChatFriendRequestLogs::getRequestUserId, friendId);
        ChatFriendRequestLogs requestLogs = getOne(wrapper);
        if (ObjectUtils.isEmpty(requestLogs)) {
            return;
        }
        requestLogs.setLookStatus(1);
        updateById(requestLogs);
    }

    @Override
    @Transactional
    public Boolean approvalFriendApplication(Long userId, Long friendId, Integer requestStatus) {
        if (requestStatus != 1 && requestStatus != 2) {
            return false;
        }
        if (requestStatus == 1) {
            chatFriendInfoService.addFriend(userId, friendId);
        }
        LambdaQueryWrapper<ChatFriendRequestLogs> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatFriendRequestLogs::getUserId, userId);
        wrapper.eq(ChatFriendRequestLogs::getRequestUserId, friendId);
        ChatFriendRequestLogs requestLogs = getOne(wrapper);
        requestLogs.setRequestStatus(requestStatus);
        return updateById(requestLogs);
    }

    @Override
    public Boolean deleteFriendApplication(Long userId, Long friendId, Integer status) {
        if (status != 1 && status != 2) {
            return false;
        }
        LambdaQueryWrapper<ChatFriendRequestLogs> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatFriendRequestLogs::getUserId, userId);
        wrapper.eq(ChatFriendRequestLogs::getRequestUserId, friendId);
        ChatFriendRequestLogs requestLogs = getOne(wrapper);
        if (ObjectUtils.isEmpty(requestLogs)) {
            return false;
        }
        if (status == 1 && requestLogs.getDataStatus() != 3) {
            //1:申请者删除申请记录
            requestLogs.setDataStatus(2);
        } else if (status == 2 && requestLogs.getDataStatus() != 2) {
            //2：被申请者删除申请记录
            requestLogs.setDataStatus(3);
        } else {
            return removeById(requestLogs.getId());
        }
        return updateById(requestLogs);
    }
}
