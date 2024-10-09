package com.king.nettyConfig.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author King
 * @version 1.0
 * @description 处理消息的handler TextWebSocketFrame： 在netty中，是用于为websocket专门处理文本的对象，frame是消息的载体
 * SimpleChannelInboundHandler：	对于请求来讲 ，相当于 【入站，入境】
 * @date 2024/10/9 13:56
 */
public class NettyWsChannelInboundHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
    }
}
