package pers.core.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.core.adapter.MqClientAdapter;
import pers.core.adapter.MqClientAdapterManager;
import pers.core.handler.MsgHandler;
import pers.core.vo.ConsumeMsgReq;
import pers.core.vo.PublishMsgReq;
import pers.core.vo.PublishMsgResp;

/**
 * 通用 mq 客户端
 * @auther ken.ck
 * @date 2022/7/12 17:55
 */
@Component
public class CommonMqClient {

    @Autowired
    public MqClientAdapterManager mqClientAdapterManager;

    public PublishMsgResp publishMsg(PublishMsgReq req) {
        MqClientAdapter mqClientAdapter = mqClientAdapterManager.getClientAdapter(req);
        return mqClientAdapter.publishMsg(req);
    }

    public void consumeMsg(ConsumeMsgReq req, MsgHandler msgHandler) {
        MqClientAdapter mqClientAdapter = mqClientAdapterManager.getClientAdapter(req);
        mqClientAdapter.consumeMsg(req, msgHandler);
    }

}
