package com.king.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.king.model.dto.ChatFriendInfoDto;
import com.king.model.po.ChatFriendInfo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author king
 * @since 2024-10-10
 */
public interface ChatFriendInfoService extends IService<ChatFriendInfo> {
    /**
     * @param userId 用户id
     * @return java.util.List<com.king.chat.model.po.ChatFriendInfo>
     * @description 查找用户
     * @author King
     * @date 2023/11/13 8:07
     */
    List<ChatFriendInfoDto> selectFriendList(Long userId);

    /**
     * @param userId
     * @param friendId
     * @return com.king.chat.model.po.ChatFriendInfo
     * @description 取消关注
     * @author King
     * @date 2023/11/13 8:54
     */
    Boolean deleteFriend(Long userId, Long friendId);

    /**
     * @param userId
     * @param friendId
     * @return java.lang.Boolean
     * @description 添加朋友,记录两天数据
     * @author King
     * @date 2024/1/24 13:53
     */
    Boolean addFriend(Long userId, Long friendId);

    /**
    * @description 修改朋友昵称
    * @param userId
     * @param friendId
    * @return java.lang.Boolean
    * @author King
    * @date 2024/10/23 14:06
    */
    Boolean updateFriendNickname(Long userId,Long friendId,String nickName);

    /**
     * @param userId
     * @param onlineStatus 在线状态（在线、离线、空值）
     * @return void
     * @description 用户上线或者下线之后更新在线状态
     * @author King
     * @date 2024/10/23 15:41
     */
    void updateOnline(Long userId, String onlineStatus);
}
