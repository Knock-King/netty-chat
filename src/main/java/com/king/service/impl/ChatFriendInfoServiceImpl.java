package com.king.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.king.mapper.ChatFriendInfoMapper;
import com.king.model.dto.ChatFriendInfoDto;
import com.king.model.po.ChatFriendInfo;
import com.king.model.po.UserInfo;
import com.king.service.ChatFriendInfoService;
import com.king.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
public class ChatFriendInfoServiceImpl extends ServiceImpl<ChatFriendInfoMapper, ChatFriendInfo> implements ChatFriendInfoService {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ChatFriendInfoMapper chatFriendInfoMapper;

    @Override
    public List<ChatFriendInfoDto> selectFriendList(Long userId) {
        LambdaQueryWrapper<ChatFriendInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatFriendInfo::getUserId, userId);
        wrapper.eq(ChatFriendInfo::getUserStatus, 1);
        List<ChatFriendInfo> chatFriendInfos = list(wrapper);
        return chatFriendInfos.stream().map(item -> {
            ChatFriendInfoDto chatFriendInfoDto = new ChatFriendInfoDto();
            BeanUtils.copyProperties(item, chatFriendInfoDto);
            return chatFriendInfoDto;
        }).collect(Collectors.toList());
    }

    @Override
    public Boolean deleteFriend(Long userId, Long friendId) {
        LambdaQueryWrapper<ChatFriendInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatFriendInfo::getUserId, userId);
        wrapper.eq(ChatFriendInfo::getFriendUserId, friendId);
        ChatFriendInfo user = getOne(wrapper);
        if (ObjectUtils.isEmpty(user)) {
            return false;
        }
        LambdaQueryWrapper<ChatFriendInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatFriendInfo::getUserId, friendId);
        queryWrapper.eq(ChatFriendInfo::getFriendUserId, userId);
        ChatFriendInfo friend = getOne(queryWrapper);
        user.setUserStatus(2);
        friend.setFriendStatus(2);
        if (user.getUserStatus() == 2 && user.getFriendStatus() == 2 && friend.getUserStatus() == 2 && friend.getFriendStatus() == 2) {
            //双方都已经删除
            List<Long> ids = Arrays.asList(user.getId(), friend.getId());
            return removeByIds(ids);
        }
        List<ChatFriendInfo> list = Arrays.asList(user, friend);
        return updateBatchById(list);
    }

    @Override
    public Boolean addFriend(Long userId, Long friendId) {
        UserInfo user = userInfoService.getById(userId);
        UserInfo friend = userInfoService.getById(friendId);
        ChatFriendInfo chatMeToFriendInfo = new ChatFriendInfo();
        chatMeToFriendInfo.setUserId(userId);
        chatMeToFriendInfo.setFriendUserId(friendId);
        chatMeToFriendInfo.setFriendRemark(friend.getNickname());
        chatMeToFriendInfo.setCreateTime(LocalDateTime.now());
        chatMeToFriendInfo.setCreateUserId(userId);
        chatMeToFriendInfo.setFriendStatus(1);
        chatMeToFriendInfo.setUserStatus(1);
        ChatFriendInfo chatFriendToMeInfo = new ChatFriendInfo();
        chatFriendToMeInfo.setUserId(friendId);
        chatFriendToMeInfo.setFriendUserId(userId);
        chatFriendToMeInfo.setFriendRemark(user.getNickname());
        chatFriendToMeInfo.setCreateTime(LocalDateTime.now());
        chatFriendToMeInfo.setCreateUserId(userId);
        chatFriendToMeInfo.setFriendStatus(1);
        chatFriendToMeInfo.setUserStatus(1);
        List<ChatFriendInfo> list = new ArrayList<>();
        list.add(chatFriendToMeInfo);
        list.add(chatMeToFriendInfo);
        return saveBatch(list);
    }

    @Override
    public Boolean updateFriendNickname(Long userId, Long friendId, String nickName) {
        LambdaQueryWrapper<ChatFriendInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatFriendInfo::getUserId, userId);
        wrapper.eq(ChatFriendInfo::getFriendUserId, friendId);
        ChatFriendInfo friendInfo = getOne(wrapper);
        if (ObjectUtils.isEmpty(friendInfo)) {
            return false;
        }
        friendInfo.setFriendRemark(nickName);
        return updateById(friendInfo);
    }

    @Override
    public void updateOnline(Long userId, String onlineStatus) {
        if (ObjectUtils.isEmpty(userId)) {
            return;
        }
        LambdaQueryWrapper<ChatFriendInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatFriendInfo::getFriendUserId, userId);
        wrapper.eq(ChatFriendInfo::getUserStatus,1);
        wrapper.eq(ChatFriendInfo::getFriendStatus,1);
        List<ChatFriendInfo> list = list(wrapper);
        List<ChatFriendInfo> infoList = list.stream().peek(item -> {
            UserInfo userinfo = userInfoService.getById(item.getFriendUserId());
            Integer status = userinfo.getOnlineStatus();
            if (status == 1) {
                item.setRemark(onlineStatus);
            } else {
                item.setRemark("");
            }
        }).collect(Collectors.toList());
        updateBatchById(infoList);
    }
}
