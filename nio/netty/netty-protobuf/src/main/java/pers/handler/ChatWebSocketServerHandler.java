package pers.handler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.Getter;
import pers.util.Code;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Sharable
public class ChatWebSocketServerHandler extends ChannelInboundHandlerAdapter {

    @Getter
    private List<IHandler> handlers = new ArrayList<>();
    private final Map<ChannelId, String> map = new ConcurrentHashMap<ChannelId, String>();
    private final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        
        try {

            for (IHandler handler : handlers) {
                if (handler.isMyMsg(msg)) {
                    handler.handle(channels, map, ctx, msg);
                    return ;
                }
            }
    
            System.out.println("unknow msg");
        
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        
        String userName = map.remove((ctx.channel().id()));
        ctx.close();

        if(userName != null) {
            String message = String.format("%s已退出聊天室", userName);
            BroadcastHandler.broadcast(channels, Code.SYS_NAME, message);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

}
