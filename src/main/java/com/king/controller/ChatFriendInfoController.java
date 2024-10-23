package com.king.controller;

import com.king.model.dto.ChatFriendInfoDto;
import com.king.model.entity.RestResponse;
import com.king.service.ChatFriendInfoService;
import com.king.utils.BaseContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author King
 * @version 1.0
 * @description 朋友信息表的接口
 * @date 2024/10/23 12:33
 */
@RestController
@RequestMapping("/chatFriendInfo")
public class ChatFriendInfoController {
    @Autowired
    private ChatFriendInfoService chatFriendInfoService;

    @GetMapping("/getChatFriendList")
    public RestResponse<List<ChatFriendInfoDto>> getChatFriendList(){
        List<ChatFriendInfoDto> chatFriendInfoDtos = chatFriendInfoService.selectFriendList(BaseContextUtils.getCurrentId());
        return RestResponse.success(chatFriendInfoDtos);
    }
}
