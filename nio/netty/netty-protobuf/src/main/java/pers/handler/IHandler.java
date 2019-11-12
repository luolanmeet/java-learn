package pers.handler;

import java.util.Map;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;

public interface IHandler {

    boolean isMyMsg(Object obj);

    void handle(ChannelGroup channels, Map<ChannelId, String> map, ChannelHandlerContext ctx, Object msg);

}
