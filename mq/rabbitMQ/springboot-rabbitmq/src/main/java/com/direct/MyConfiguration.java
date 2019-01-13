package com.direct;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author cck
 */
@Configuration
public class MyConfiguration {
    
    @Value("${myQueueName}")
    private String queueName;
    
    @Bean
    public Queue queue() {
        return new Queue(queueName);
    }
}
