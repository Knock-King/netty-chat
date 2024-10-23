package com.king.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author King
 * @version 1.0
 * @description 用于返回给前端展示的用户信息
 * @date 2024/10/23 15:22
 */
@Data
public class UserShowInfo {
    private Long id;
    /**
     * 账户
     */
    private Long accountNumber;
    /**
     * 用户头像
     */
    private String pictureUrl;
    /**
     * 性别    男    ;  女
     */
    private String gender;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 个人说明
     */
    private String description;
}
