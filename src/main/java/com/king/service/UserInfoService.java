package com.king.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.king.model.po.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author King
 * @version 1.0
 * @description 用户信息service
 * @date 2024/10/21 15:48
 */
public interface UserInfoService  extends IService<UserInfo> {
    /**
     * @param userInfo
     * @param httpServletRequest
     * @return java.lang.Boolean
     * @description 用户登录逻辑,更新使用者ip
     * @author King
     * @date 2024/10/21 16:07
     */
    Boolean login(UserInfo userInfo, HttpServletRequest httpServletRequest, HttpSession httpSession);

    /**
    * @description 用户注册逻辑
    * @param userInfo
    * @return java.lang.Boolean
    * @author King
    * @date 2024/10/21 16:07
    */
    Boolean register(UserInfo userInfo, HttpSession httpSession);

    /**
    * @description 用户登出
    * @param httpSession
    * @return java.lang.Boolean
    * @author King
    * @date 2024/10/21 16:45
    */
    Boolean logout(HttpSession httpSession);

    /**
    * @description TODO
     * @param oldPassword
     * @param accountNumber
    * @return java.lang.Boolean
    * @author King
    * @date 2024/10/21 16:48
    */
    Boolean updatePassword(String newPassword, String oldPassword, Long accountNumber);

}
