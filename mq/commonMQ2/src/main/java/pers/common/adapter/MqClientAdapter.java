package pers.common.adapter;

import pers.common.listener.MsgListener;
import pers.common.vo.ConsumeMsgReq;
import pers.common.vo.PublishMsgReq;

/**
 * @auther ken.ck
 * @date 2022/7/11 14:52
 */
public abstract class MqClientAdapter {

    /**
     * 推送消息
     * @param req
     */
    public abstract void publishMsg(PublishMsgReq req);

    /**
     * 注册消息监听
     * @param req
     * @param msgListener
     */
    public abstract void consumeMsg(ConsumeMsgReq req, MsgListener msgListener);

}
