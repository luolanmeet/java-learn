package pers.handler;

import io.netty.channel.group.ChannelGroup;
import pers.util.Code;

public class BroadcastHandler {

    public static void broadcast(ChannelGroup channels, String name, String message) {

        MsgProto.Msg borderMsg = Msg.newBuilder()
                .setType(Code.CHAT_MSG)
                .setUserName(name)
                .setMsg(message)
                .build();

        channels.writeAndFlush(borderMsg);
    }

}
