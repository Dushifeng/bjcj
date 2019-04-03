package cn.lovezsm.bjcj.netty;

import io.netty.bootstrap.Bootstrap;

import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import io.netty.channel.socket.nio.NioDatagramChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UDPServer{
    private final int port;
    private Logger log = LoggerFactory.getLogger(this.getClass());
    public UDPServer(int port) {
        this.port = port;
    }
    public void start() throws Exception {
        final UDPServerHandler serverHandler = new UDPServerHandler();
        log.info("启动服务");
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new UDPServerHandler());

            b.bind(port).sync().channel().closeFuture().await();
        }finally {
            group.shutdownGracefully().sync();
        }
    }



}
