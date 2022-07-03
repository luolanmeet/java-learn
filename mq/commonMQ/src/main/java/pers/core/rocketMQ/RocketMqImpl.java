package pers.core.rocketMQ;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import pers.common._interface.MqService;
import pers.common.model.SendMsgReq;
import pers.common.model.SendMsgResp;

/**
 * @auther ken.ck
 * @date 2022/7/2 16:25
 */
public class RocketMqImpl implements MqService {

    private RocketMQTemplate template;

    public RocketMqImpl(RocketMQTemplate template) {
        this.template = template;
    }

    @Override
    public SendMsgResp sendMsg(SendMsgReq req) {

        SendMsgResp resp = new SendMsgResp();

        String destination = req.getTopicName();
        if (req.getTag() != null && !req.getTag().isEmpty()) {
            destination = destination + ":" + req.getTag();
        }

        template.convertAndSend(destination, req.getMessage());

        return resp;
    }

}
