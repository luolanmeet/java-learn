package pers;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import pers.handler.ChatHandler;
import pers.handler.ChatWebSocketServerHandler;
import pers.handler.IHandler;
import pers.handler.LoginHandler;
import pers.util.ChatChannelInitializer;

import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    private static final int port = 8899;

    public void run() throws InterruptedException {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    
        ChatWebSocketServerHandler handler = initHandler();
        
        try {
            ServerBootstrap wsBootstrap = new ServerBootstrap();
            wsBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChatChannelInitializer(handler))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            System.out.println("websocket server already start");

            ChannelFuture future = wsBootstrap.bind(port).sync();
            future.channel().closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
    
    private ChatWebSocketServerHandler initHandler() {
    
        ChatWebSocketServerHandler handler = new ChatWebSocketServerHandler();
    
        handler.getHandlers().add(new LoginHandler());
        handler.getHandlers().add(new ChatHandler());
        
        return handler;
    }
    
    public static void main(String[] args) throws InterruptedException {
        new ChatServer().run();
    }

}
