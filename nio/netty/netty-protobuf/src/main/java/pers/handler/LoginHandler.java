package pers.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import pers.util.Code;

import java.util.Map;

public class LoginHandler implements IHandler {

    private static final Integer SUCCESS = 1;
    private static final Integer FAIL = 2;

    public void handle(ChannelGroup channels, Map<ChannelId, String> map, ChannelHandlerContext ctx, Object msg) {
        LoginReq req = (LoginReq) msg;
        String userName = req.getUserName();
        String password = req.getPassword();

        System.out.println(("==============LOGIN================");
        int resultCode = true ? SUCCESS : FAIL;

        LoginResp resp = LoginResp.newBuilder()
                .setType(Code.LOGIN_RESP)
                .setResultCode(resultCode)
                .build();
        ctx.writeAndFlush(resp);

        /**
         * 广播用户登录消息 & 记录登陆者信息
         */
        if(resultCode == SUCCESS) {
            channels.add(ctx.channel());
            map.put(ctx.channel().id(), userName);
            String chatMessage = String.format("欢迎%s加入聊天室", userName);
            BroadcastHandler.broadcast(channels, Code.SYS_NAME, chatMessage);
        } else {
            ctx.close();
        }

    }

    public boolean isMyMsg(Object obj) {
        return obj instanceof LoginReqProto.LoginReq;
    }

}
