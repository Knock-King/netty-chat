package com.king.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.king.exception.NettyChatException;
import com.king.mapper.UserInfoMapper;
import com.king.model.po.UserInfo;
import com.king.service.UserInfoService;
import com.king.utils.IdWorkerUtils;
import com.king.utils.IpUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author King
 * @version 1.0
 * @description 用户信息实现类
 * @date 2024/10/21 15:49
 */
@Service
@Log4j
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public Boolean login(UserInfo userInfo, HttpServletRequest httpServletRequest) {
        if (ObjectUtils.isEmpty(userInfo.getAccountNumber())) {
            NettyChatException.cast("请输入账户");
        }
        if (ObjectUtils.isEmpty(userInfo.getPassword())) {
            NettyChatException.cast("请输入密码");
        }
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserInfo::getAccountNumber, userInfo.getAccountNumber());
        UserInfo info = getOne(wrapper);
        if (info.getPassword().equals(userInfo.getPassword())) {
            NettyChatException.cast("账号和密码不匹配，请重新再试");
        }
        String ipAddr = IpUtil.getIpAddr(httpServletRequest);
        info.setIpAddress(ipAddr);
        info.setIpAddressTime(LocalDateTime.now());
        updateById(info);
        return true;
    }

    @Override
    public Boolean register(UserInfo userInfo) {
        userInfo.setRegisterTime(LocalDateTime.now());
        userInfo.setAccountNumber(IdWorkerUtils.getInstance().nextId());
        userInfo.setUserStatus(1);
        return save(userInfo);
    }
}
