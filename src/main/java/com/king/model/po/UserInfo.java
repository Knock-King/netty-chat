package com.king.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author King
 * @version 1.0
 * @description 用户信息表
 * @date 2024/10/9 18:16
 */
@Data
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
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
     * 头像id
     */
    private String pictureId;
    /**
     * 性别    男    ;  女
     */
    private String gender;
    /**
     * 生日	年月日
     */
    private Date birthday;
    /**
     * 用户名		最长15位 最短8位
     */
    private String username;
    /**
     * 密码    最长25位 最短8位
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 用户状态   1、正常     2、停用
     */
    private Integer userStatus;
    /**
     * 注册时间
     */
    private LocalDateTime registerTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 个人说明
     */
    private String description;
    /**
     * ip地址
     */
    private String ipAddress;
    /**
     * 获取ip地址的最新时间
     */
    private LocalDateTime ipAddressTime;
}
