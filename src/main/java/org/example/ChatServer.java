package org.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ChatServer {

    private final int port;
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    public ChatServer(int port) {
        this.port = port;
        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();
    }

    public void start() {
        var bootstrap = new ServerBootstrap();

        try {
            bootstrap
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            var pipeline = socketChannel.pipeline();
                            pipeline.addLast(new PacketEncoder());
                            pipeline.addLast(new ChatServerHandler());
                        }
                    }).bind(port).sync().channel().closeFuture().sync();

            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
