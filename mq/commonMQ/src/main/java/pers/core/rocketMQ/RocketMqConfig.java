package pers.core.rocketMQ;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.common._interface.MqService;
import pers.common.enums.MqType;

/**
 * @auther ken.ck
 * @date 2022/7/2 17:33
 */
@Configuration
@ConditionalOnExpression("'${common.mq.enable}'.contains('<rocketmq>')")
public class RocketMqConfig {

    @Autowired
    private RocketMQTemplate template;

    @Bean(MqType.ROCKET_MQ)
    public MqService rocketMq() {
        return new RocketMqImpl(template);
    }

}
