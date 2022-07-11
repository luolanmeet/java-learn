package pers.mq.rocketmq;

import lombok.Setter;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import pers.common.adapter.MqClientAdapter;
import pers.common.listener.MsgListener;
import pers.common.vo.ConsumeMsgReq;
import pers.common.vo.PublishMsgReq;

/**
 * @auther ken.ck
 * @date 2022/7/11 15:50
 */
public class RocketMqClientAdapter extends MqClientAdapter {

    @Setter
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void publishMsg(PublishMsgReq req) {
        String destination = req.getTopicName();
        rocketMQTemplate.convertAndSend(destination, req.getMessage());
    }

    @Override
    public void consumeMsg(ConsumeMsgReq req, MsgListener msgListener) {

    }
}
