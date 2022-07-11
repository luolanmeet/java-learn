package pers.common.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.common.adapter.MqClientAdapter;
import pers.common.adapter.MqClientAdapterManager;
import pers.common.listener.MsgListener;
import pers.common.vo.ConsumeMsgReq;
import pers.common.vo.CreateInstanceReq;
import pers.common.vo.PublishMsgReq;

/**
 * @auther ken.ck
 * @date 2022/7/11 14:44
 */
@Component
public class CommonMqClient {

    @Autowired
    public MqClientAdapterManager mqClientAdapterManager;

    public void createMqInstance(CreateInstanceReq req) {
        mqClientAdapterManager.getClientAdapter(req);
    }

    public void publishMsg(PublishMsgReq req) {
        MqClientAdapter mqClientAdapter = mqClientAdapterManager.getClientAdapter(req);
        mqClientAdapter.publishMsg(req);
    }

    public void consumeMsg(ConsumeMsgReq req, MsgListener msgListener) {
        MqClientAdapter mqClientAdapter = mqClientAdapterManager.getClientAdapter(req);
        mqClientAdapter.consumeMsg(req, msgListener);
    }

}
