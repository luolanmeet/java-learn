package com.fanout.publisher;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fanout.MyConfiguration;

/**
 * 
 * @author cck
 */
@Component
public class MyPublisher {
    
    @Autowired
    private AmqpTemplate template;
    
    public void sendA() {
        
        String msg = "A hello world";
        template.convertAndSend(MyConfiguration.EXCHANGE_NAME, "", msg);
    }
    
    public void sendB() {
        
        String msg = "B hello world";
        template.convertAndSend(MyConfiguration.EXCHANGE_NAME, "", msg);
    }
}
