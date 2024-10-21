package com.king.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.king.mapper.ChatFriendInfoMapper;
import com.king.model.dto.ChatFriendInfoDto;
import com.king.model.po.ChatFriendInfo;
import com.king.service.ChatFriendInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Override
    public List<ChatFriendInfoDto> selectFriendList(Long userId) {
        return null;
    }

    @Override
    public Boolean deleteFriend(Long userId, Long friendId) {
        return null;
    }

    @Override
    public Boolean addFriend(Long userId, Long friendId) {
        return null;
    }
}
