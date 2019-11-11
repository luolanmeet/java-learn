package pers.factorial.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import pers.factorial.codecs.BigIntegerDecoder;
import pers.factorial.codecs.NumberEncoder;

/**
 * 计算阶乘结果的服务端
 */
public class FactorialServer {
    
    private static final int PORT = 8080;
    
    public static void main(String[] args) throws Exception {
    
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
    
        try {
            
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new BigIntegerDecoder())
                                    .addLast(new NumberEncoder())
                                    .addLast(new FactorialServerHandler());
                        }
                    });
    
            // 服务端阻塞
            b.bind(PORT).sync().channel().closeFuture().sync();
            
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
