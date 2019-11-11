package pers.factorial.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import pers.factorial.codecs.BigIntegerDecoder;
import pers.factorial.codecs.NumberEncoder;

/**
 * 计算阶乘结果的客户端
 */
public class FactorialClient {
    
    public static final int COUNT = 100;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;
    
    public static void main(String[] args) throws Exception {
    
        EventLoopGroup group = new NioEventLoopGroup();
    
        try {
            
            Bootstrap b = new Bootstrap();
            final FactorialClientHandler handler = new FactorialClientHandler();
            b.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new BigIntegerDecoder())
                                    .addLast(new NumberEncoder())
                                    .addLast(handler);
                        }
                    });
    
            b.connect(HOST, PORT).sync();
            System.err.format("Factorial of %,d is: %,d \n", COUNT, handler.getFactorial());
            
        } finally {
            group.shutdownGracefully();
        }
        
    }

}
