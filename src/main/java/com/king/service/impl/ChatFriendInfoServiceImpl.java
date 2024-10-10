package com.king.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.king.mapper.ChatFriendInfoMapper;
import com.king.model.dto.ChatFriendInfoDto;
import com.king.model.po.ChatFriendInfo;
import com.king.model.po.ChatFriendMsgLogs;
import com.king.model.po.UserInfo;
import com.king.service.ChatFriendInfoService;
import com.king.service.ChatFriendMsgLogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testng.util.TimeUtils;

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
