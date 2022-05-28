package io.seata.core.rpc;

import io.netty.channel.Channel;
import io.seata.core.protocol.RpcMessage;
import io.seata.core.rpc.processor.RemotingProcessor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeoutException;

/**
 * 远程服务接口
 * @date 2022/5/27 21:09
 */
public interface RemotingServer {

    /**
     * 服务端发送同步请求
     * @param resourceId rm client resourceId
     * @param clientId   rm client id
     * @param msg        transaction message {@link io.seata.core.protocol}
     * @return client result message
     * @throws TimeoutException TimeoutException
     */
    Object sendSyncRequest(String resourceId, String clientId, Object msg) throws TimeoutException;

    /**
     * server send sync request.
     * 服务端发送同步请求
     * @param channel client channel
     * @param msg     transaction message {@link io.seata.core.protocol}
     * @return client result message
     * @throws TimeoutException TimeoutException
     */
    Object sendSyncRequest(Channel channel, Object msg) throws TimeoutException;

    /**
     * server send async request.
     * 服务端发送异步请求
     * @param channel client channel
     * @param msg     transaction message {@link io.seata.core.protocol}
     */
    void sendAsyncRequest(Channel channel, Object msg);

    /**
     * server send async response.
     * 服务端发送异步响应
     * @param rpcMessage rpc message from client request
     * @param channel    client channel
     * @param msg        transaction message {@link io.seata.core.protocol}
     */
    void sendAsyncResponse(RpcMessage rpcMessage, Channel channel, Object msg);

    /**
     * register processor
     * 注册处理器
     * @param messageType {@link io.seata.core.protocol.MessageType}
     * @param processor   {@link RemotingProcessor}
     * @param executor    thread pool
     */
    void registerProcessor(final int messageType, final RemotingProcessor processor, final ExecutorService executor);

}
