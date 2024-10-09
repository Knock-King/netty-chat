package com.king.nettyConfig;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author King
 * @version 1.0
 * @description netty 服务
 * @date 2024/10/9 11:47
 */
@Component
@Slf4j
public class NettyServer {
    /**
     * netty端口
     */
    @Value("${netty.server.port}")
    private static int port = 52002;
    private EventLoopGroup mainGroup;
    private EventLoopGroup subGroup;
    private ServerBootstrap server;
    private ChannelFuture future;

    //初始化参数
    public NettyServer() {
        mainGroup = new NioEventLoopGroup();
        subGroup = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(mainGroup, subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new NettyChannelInitializer());
    }
    public static NettyServer getInstance() {
        return SingletionWSServer.instance;
    }
    //创建一个单例的对象
    private static class SingletionWSServer {
        static final NettyServer instance = new NettyServer();
    }
    //netty启动方法
    public void start() {
        this.future = server.bind(port);
        log.info("netty server server 启动完毕... port = " + port);
    }

}
