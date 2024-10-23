package com.king.model.dto;

import com.king.model.po.ChatFriendRequestLogs;
import lombok.Data;

/**
 * @author King
 * @version 1.0
 * @description ChatFriendRequestLogs Dto
 * @date 2024/10/23 14:13
 */
@Data
public class ChatFriendRequestLogsDto extends ChatFriendRequestLogs {
    private String requestUserNickname;
}
