package com.useful.publisher;

import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author cck
 */
@Component
public class MyPublisher {
    
    @Autowired
    private RabbitMessagingTemplate rabbitTemplate;
    
    private String exchangeName = "cck.fanout";
    
    public void send() {
        rabbitTemplate.convertAndSend(exchangeName, "", "hello");
    }
}
