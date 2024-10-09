package com.king.config.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author King
 * @version 1.0
 * @description 自定义http的Handler
 * @date 2024/10/9 13:58
 */
@Slf4j
public class NettyHttpChannelInboundHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        // 获得channel --后面可能会有用
        Channel channel = ctx.channel();

        // 获得消息
        String msgStr = msg.toString();
        log.info(" msgStr = " + msgStr);

        ByteBuf msgByteBuf = Unpooled.copiedBuffer("hello netty", CharsetUtil.UTF_8);
        //msgByteBuf.

        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK, msgByteBuf);

        // 为响应增加数据类型和长度
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, msgByteBuf.readableBytes());

        // 把响应返回客户端
        ctx.writeAndFlush(response);

    }
}
