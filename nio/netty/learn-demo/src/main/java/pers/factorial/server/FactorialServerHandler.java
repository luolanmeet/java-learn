package pers.factorial.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.math.BigInteger;

/**
 * 做阶乘的逻辑代码
 */
public class FactorialServerHandler extends SimpleChannelInboundHandler<BigInteger> {
    
    private BigInteger lastMultiplier = new BigInteger("1");
    private BigInteger factorial = new BigInteger("1");
    
    /**
     * 接收到消息之后，做阶乘的运算，再把结果发送给客户端
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BigInteger msg) throws Exception {
        lastMultiplier = msg;
        factorial = factorial.multiply(msg);
        ctx.writeAndFlush(factorial);
    }
    
    /***
     * 链接关闭
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.err.printf("Factorial of %,d is: %,d%n", lastMultiplier, factorial);
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
    
}
