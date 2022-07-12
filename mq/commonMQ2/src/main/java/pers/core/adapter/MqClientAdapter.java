package pers.core.adapter;


import pers.core.handler.MsgHandler;
import pers.core.vo.ConsumeMsgReq;
import pers.core.vo.PublishMsgReq;
import pers.core.vo.PublishMsgResp;

/**
 * mq 客户端适配类
 * @auther ken.ck
 * @date 2022/7/12 17:52
 */
public abstract class MqClientAdapter {

    /**
     * 推送消息
     * @param req
     */
    public abstract PublishMsgResp publishMsg(PublishMsgReq req);

    /**
     * 注册消息监听
     * @param req
     * @param msgHandler
     */
    public abstract void consumeMsg(ConsumeMsgReq req, MsgHandler msgHandler);

    /**
     * 应用关闭，回收资源
     */
    public abstract void shutdown();

}
