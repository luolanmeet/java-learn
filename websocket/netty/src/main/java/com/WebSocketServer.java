package com;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 
 * @author cck
 */
public class WebSocketServer {
    
    final static Integer PORT = 8989;
    
    public void run () throws InterruptedException {
        
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                 .channel(NioServerSocketChannel.class)
                 .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new HttpServerCodec())
                                .addLast(new HttpObjectAggregator(64*1024))
                                .addLast(new ChunkedWriteHandler())
                                .addLast(new HttpRequestHandler("/ws"))
                                .addLast(new WebSocketServerProtocolHandler("/ws"))
                                .addLast(new TextWebSocketFrameHandler());
                    }
                });
        
        Channel ch = bootstrap.bind(PORT).sync().channel();
        System.out.println("Web socket server started at port " + PORT);
        ch.closeFuture().sync();
    }
    
    
    public static void main(String[] args) throws InterruptedException {
        new WebSocketServer().run();
    }
    
}
