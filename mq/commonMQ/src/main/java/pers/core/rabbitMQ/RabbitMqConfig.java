package pers.core.rabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.common._interface.MqService;
import pers.common.enums.MqType;

/**
 * @auther ken.ck
 * @date 2022/7/2 17:32
 */
@Configuration
@ConditionalOnExpression("'${common.mq.enable}'.contains('<rabbitmq>')")
public class RabbitMqConfig {

    @Autowired
    private RabbitTemplate template;

    @Bean(MqType.RABBIT_MQ)
    public MqService rabbitMq() {
        return new RabbitMqImpl(template);
    }

}
