package com.king.config.handler;

import com.king.config.user.UserChannelRel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author King
 * @version 1.0
 * @description 用于检测channel的心跳handler
 * @date 2024/10/9 13:48
 */
@Slf4j
public class NettyHeartBeatHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 判断evt是否是IdleStateEvent（用于触发用户事件，包含 读空闲/写空闲/读写空闲 ）
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;        // 强制类型转换
            if (event.state() == IdleState.READER_IDLE) {
                log.info("进入读空闲...");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                log.info("进入写空闲...");
            } else if (event.state() == IdleState.ALL_IDLE) {
                log.info("所有的空闲...");
                ChannelGroup users = UserChannelRel.users;
                log.info("channel关闭前，users的数量为：" + users.size());
                Channel channel = ctx.channel();
                // 关闭无用的channel，以防资源浪费
                channel.close();
                log.info("channel关闭后，users的数量为：" + users.size());
            }
        }
    }

}
