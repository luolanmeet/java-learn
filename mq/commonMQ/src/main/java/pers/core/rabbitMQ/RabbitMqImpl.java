package pers.core.rabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import pers.common._interface.MqService;
import pers.common.model.SendMsgReq;
import pers.common.model.SendMsgResp;

/**
 * @auther ken.ck
 * @date 2022/7/2 15:50
 */
public class RabbitMqImpl implements MqService {

    private RabbitTemplate template;

    public RabbitMqImpl(RabbitTemplate template) {
        this.template = template;
    }

    @Override
    public SendMsgResp sendMsg(SendMsgReq req) {

        SendMsgResp resp = new SendMsgResp();

        if (req.getQueueName() != null && !req.getQueueName().isEmpty()) {
            template.convertAndSend(req.getQueueName(), req.getMessage());
            return resp;
        }

        template.convertAndSend(req.getExchangeName(), req.getRoutingKey(), req.getMessage());
        return resp;
    }
}
