package com.king.controller;

import com.king.model.dto.UserShowInfo;
import com.king.model.entity.RestResponse;
import com.king.model.po.UserInfo;
import com.king.service.UserInfoService;
import com.king.utils.BaseContextUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author King
 * @version 1.0
 * @description 用户信息controller
 * @date 2024/10/23 15:19
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/getUserInfo")
    public RestResponse<UserShowInfo> getUserInfo() {
        UserInfo userInfo = userInfoService.getById(BaseContextUtils.getCurrentId());
        UserShowInfo userShowInfo = new UserShowInfo();
        BeanUtils.copyProperties(userInfo, userShowInfo);
        return RestResponse.success(userShowInfo);
    }
}
