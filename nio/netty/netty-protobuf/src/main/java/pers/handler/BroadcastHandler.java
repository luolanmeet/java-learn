package pers.handler;

import io.netty.channel.group.ChannelGroup;
import pers.protobuf.MsgProto;
import pers.util.Code;

/**
 * 广播消息
 */
public class BroadcastHandler {

    public static void broadcast(ChannelGroup channels, String name, String message) {

        MsgProto.Msg borderMsg = MsgProto.Msg.newBuilder()
                .setType(Code.CHAT_MSG)
                .setUserName(name)
                .setMsg(message)
                .build();

        channels.writeAndFlush(borderMsg);
    }

}
