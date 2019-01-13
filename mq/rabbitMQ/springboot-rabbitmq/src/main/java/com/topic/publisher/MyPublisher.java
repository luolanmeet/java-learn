package com.topic.publisher;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.topic.MyConfiguration;

/**
 * 
 * @author cck
 */
@Component
public class MyPublisher {
    
    @Autowired
    private AmqpTemplate template;
    
    private String routingKeyA = "topic.queueA";
    private String routingKeyB = "topic.queue";
    
    public void sendA() {
        String msg = "A hello world";
        template.convertAndSend(MyConfiguration.EXCHANGE_NAME, routingKeyA, msg);
    }
    
    public void sendB() {
        String msg = "B hello world";
        template.convertAndSend(MyConfiguration.EXCHANGE_NAME, routingKeyB, msg);
    }
}
