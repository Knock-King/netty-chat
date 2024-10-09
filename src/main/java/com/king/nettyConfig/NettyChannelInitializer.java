package com.king.nettyConfig;

import com.king.nettyConfig.handler.NettyHeartBeatHandler;
import com.king.nettyConfig.handler.NettyWsChannelInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author King
 * @version 1.0
 * @description 管道初始化
 * @date 2024/10/9 11:54
 */
@Slf4j
public class NettyChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        log.info(" 管道正在初始化...... ");
        /**
         * ChannelPipeline类是ChannelHandler实例对象的链表，用于处理或截获通道的接收和发送数据。它提供了一种高级的截取过滤模式（类似serverlet中的filter功能），让用户可以在ChannelPipeline中完全控制一个事件以及如何处理ChannelHandler与ChannelPipeline的交互。
         * 对于每个新的通道Channel，都会创建一个新的ChannelPipeline，并将器pipeline附加到channel中。
         * ChannelPipeline可以动态添加、删除、替换其中的ChannelHandler，这样的机制可以提高灵活性
         * */
        ChannelPipeline pipeline = socketChannel.pipeline();

        // websocket 基于http协议，所以要有http编解码器
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());

        // 对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());

        // 对httpMessage进行聚合，聚合成FullHttpRequest或FullHttpResponse
        // 几乎在netty中的编程，都会使用到此hanler
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));

        // 增加心跳支持 start
        // 针对客户端，如果在1分钟时没有向服务端发送读写心跳(ALL)，则主动断开
        // 如果是读空闲或者写空闲，不处理
        //readerIdleTimeSeconds：读超时。即当在指定的时间间隔内没有从 Channel 读取到数据时，会触发一个 READER_IDLE 的 IdleStateEvent 事件
        //writerIdleTimeSeconds: 写超时。即当在指定的时间间隔内没有数据写入到 Channel 时，会触发一个 WRITER_IDLE 的 IdleStateEvent 事件
        //allIdleTimeSeconds: 读/写超时。即当在指定的时间间隔内没有读或写操作时，会触发一个 ALL_IDLE 的 IdleStateEvent 事件
        pipeline.addLast(new IdleStateHandler(8, 10, 12));

        // 自定义的空闲状态检测
        pipeline.addLast(new NettyHeartBeatHandler());

        // 以下是支持httpWebsocket
        /**
         * websocket 服务器处理的协议，用于指定给客户端连接访问的路由 : /ws
         * 本handler会帮你处理一些繁重的复杂的事
         * 会帮你处理握手动作： handshaking（close, ping, pong） ping + pong = 心跳
         * 对于websocket来讲，都是以frames进行传输的，不同的数据类型对应的frames也不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        // 自定义的wshandler
        pipeline.addLast(new NettyWsChannelInboundHandler());

    }
}
