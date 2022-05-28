package io.seata.core.rpc.processor;

import io.netty.channel.ChannelHandlerContext;
import io.seata.core.protocol.RpcMessage;

/**
 * @date 2022/5/27 21:21
 */
public interface RemotingProcessor {

    /**
     * Process message
     * 处理消息
     * @param ctx        Channel handler context.
     * @param rpcMessage rpc message.
     * @throws Exception throws exception process message error.
     */
    void process(ChannelHandlerContext ctx, RpcMessage rpcMessage) throws Exception;

}
