package com.king.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.king.model.po.ChatFriendMsgLogs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author king
 */
@Mapper
public interface ChatFriendMsgLogsMapper extends BaseMapper<ChatFriendMsgLogs> {
    public List<ChatFriendMsgLogs> getMsgLogsByOneUserId(@Param("userId") Long userId);

}
