package com.king.config.handler;

import com.king.config.user.UserChannelRel;
import com.king.model.entity.DataContent;
import com.king.model.enums.MsgActionEnum;
import com.king.utils.JsonUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import static com.king.config.user.UserChannelRel.users;

/**
 * @author King
 * @version 1.0
 * @description 处理消息的handler TextWebSocketFrame： 在netty中，是用于为websocket专门处理文本的对象，frame是消息的载体
 * SimpleChannelInboundHandler：	对于请求来讲 ，相当于 【入站，入境】
 * @date 2024/10/9 13:56
 */
@Slf4j
public class NettyWsChannelInboundHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 获得channel
        Channel currentChannel = ctx.channel();
        // 获取客户端传输过来的消息
        String content = msg.text();
        //转成实体类对象
        DataContent dataContent = JsonUtils.jsonToPojo(content, DataContent.class);
        if (ObjectUtils.isEmpty(dataContent)) {
            return;
        }
        Integer action = dataContent.getAction();
        //判断消息类型，根据不同的类型来处理不同的业务
        if (MsgActionEnum.CONNECT.type.equals(action)) {
            //第一次(或重连)初始化连接
            //当websocket 第一次open的时候，初始化channel，把用的channel和userid关联起来
            String sendId = dataContent.getChatMsg().getSendId();
            UserChannelRel.put(sendId, currentChannel);
        } else if (MsgActionEnum.CHAT.type.equals(action)) {
            //聊天消息
        } else if (MsgActionEnum.SIGNED.type.equals(action)) {
            //消息签收
        } else if (MsgActionEnum.KEEPALIVE.type.equals(action)) {
            //客户端保持心跳
            log.info("收到来自channel为[" + currentChannel + "]的心跳包...");
        } else if (MsgActionEnum.PULL_FRIEND.type.equals(action)) {
            //拉取好友入群
        } else if (MsgActionEnum.FRIEND_REQUEST.type.equals(action)) {
            //好友申请
        } else if (MsgActionEnum.GROUP_MSG.type.equals(action)) {
            //群聊消息
        }
    }

    /**
     * 当客户端连接服务端之后（打开连接）
     * 获取客户端的channle，并且放到ChannelGroup中去进行管理
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        users.add(ctx.channel());
        log.info(" netty 获得连接.....	");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        String channelId = ctx.channel().id().asShortText();
        // 当触发handlerRemoved，ChannelGroup会自动移除对应客户端的channel
        users.remove(ctx.channel());
        log.info("客户端被移除，channelId为：" + channelId);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 发生异常之后关闭连接（关闭channel），随后从ChannelGroup中移除
        ctx.channel().close();
        users.remove(ctx.channel());
        log.info(" netty 异常了...... ");
    }
}
