package com.king.config.user;

import com.king.utils.ValidationUtils;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author King
 * @version 1.0
 * @description 用户管理器
 * @date 2024/10/9 13:51
 */
@Slf4j
public class UserChannelRel {
    /**
     * 用于记录和管理所有在线客户端的channle 在线
     */
    public static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    //用户id和channel的关联关系处理 所有用户
    private static Map<String, Channel> manager = new ConcurrentHashMap<>();

    public static void put(String senderId, Channel channel) {
        manager.put(senderId, channel);
    }

    public static Channel get(String senderId) {
        return manager.get(senderId);
    }

    public static void output() {
        for (HashMap.Entry<String, Channel> entry : manager.entrySet()) {
            log.info(" imChat获得连接：  UserId: " + entry.getKey()
                    + ", ChannelId: " + entry.getValue().id().asLongText());
        }
    }

    public static String getUserIdByChannel(Channel channel) {
        List<String> key = getKey(manager, channel);
        if (ValidationUtils.isListEmpty(key)) {
            return null;
        }
        return key.get(0);
    }

    /**
     * @param map
     * @param value
     * @return java.lang.Object
     * @description 根据value获取key集合
     * @author King
     * @date 2024/10/23 16:12
     */
    public static List<String> getKey(Map<String, Channel> map, Channel value) {
        List<String> keyList = new ArrayList<>();
        for (String key : map.keySet()) {
            if (map.get(key).equals(value)) {
                keyList.add(key);
            }
        }
        return keyList;
    }
}
