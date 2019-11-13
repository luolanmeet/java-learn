package pers.handler;

import java.util.Map;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import pers.protobuf.MsgProto;

/**
 * 聊天的处理类
 */
public class ChatHandler implements IHandler {

    public void handle(ChannelGroup channels, Map<ChannelId, String> map, ChannelHandlerContext ctx, Object msg) {
        
        MsgProto.Msg message = (MsgProto.Msg) msg;
        String userName = message.getUserName();
        String chatMessage = message.getMsg();

        BroadcastHandler.broadcast(channels, userName, chatMessage);
    }

    public boolean isMyMsg(Object obj) {
        return obj instanceof MsgProto.Msg;
    }

}
