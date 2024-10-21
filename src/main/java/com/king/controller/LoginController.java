package com.king.controller;

import com.king.model.entity.RestResponse;
import com.king.model.po.UserInfo;
import com.king.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author King
 * @version 1.0
 * @description 登录相关接口
 * @date 2024/10/21 15:44
 */
@ResponseBody
public class LoginController {
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/login")
    public RestResponse<Boolean> login(@RequestBody UserInfo userInfo, HttpServletRequest httpServletRequest){
        Boolean flag = userInfoService.login(userInfo,httpServletRequest);
        return RestResponse.success(flag);
    }

    @PostMapping("/register")
    public RestResponse<Boolean> register(@RequestBody UserInfo userInfo){
        Boolean flag = userInfoService.register(userInfo);
        return RestResponse.success(flag);
    }
}
