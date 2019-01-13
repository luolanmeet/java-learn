package com.direct.publisher;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * @author cck
 */
@Component
public class MyPublisher {
    
    @Autowired
    private AmqpTemplate template;
    
    @Value("${myQueueName}")
    private String routingKey;
    
    public void send() {
        
        String obj = "hello";
        template.convertAndSend(routingKey, obj);
    }
}
