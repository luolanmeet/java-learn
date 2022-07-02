package pers.core.rabbitMQ;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import pers.common._interface.MqService;
import pers.common.enums.MqType;

/**
 * @auther ken.ck
 * @date 2022/7/2 17:32
 */
@Configuration
@ConditionalOnExpression("'${common.mq.enable}'.contains('<rabbitmq>')")
public class RabbitMqConfig {

    @Bean(MqType.RABBIT_MQ)
    public MqService rabbitMq() {
        return new RabbitMqImpl();
    }

}
