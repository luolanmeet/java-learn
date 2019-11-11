package pers.factorial.client;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FactorialClientHandler extends SimpleChannelInboundHandler<BigInteger> {
    
    private ChannelHandlerContext ctx;
    private int receivedMessages;
    private int next = 1;
    final BlockingQueue<BigInteger> answer = new LinkedBlockingQueue<>();
    
    public BigInteger getFactorial() {
    
        try {
            // 在此阻塞，知道结果被放入队列中
            return answer.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.ctx = ctx;
        sendNumbers();
    }
    
    @Override
    public void channelRead0(ChannelHandlerContext ctx, final BigInteger msg) {
        receivedMessages++;
        if (receivedMessages == FactorialClient.COUNT) {
            
            // 连接成功关闭之后，将结果msg添加到阻塞队列中。
            ctx.channel().close().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) {
                    boolean offered = answer.offer(msg);
                    assert offered;
                }
            });
        }
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
    private void sendNumbers() {
        // Do not send more than 4096 numbers.
        ChannelFuture future = null;
        for (int i = 0; i < 4096 && next <= FactorialClient.COUNT; i++) {
            future = ctx.write(Integer.valueOf(next));
            next++;
        }
        
        // 防止写失败的逻辑
        if (next <= FactorialClient.COUNT) {
            assert future != null;
            future.addListener(numberSender);
        }
        ctx.flush();
    }
    
    private final ChannelFutureListener numberSender = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            if (future.isSuccess()) {
                sendNumbers();
            } else {
                future.cause().printStackTrace();
                future.channel().close();
            }
        }
    };
    
}
