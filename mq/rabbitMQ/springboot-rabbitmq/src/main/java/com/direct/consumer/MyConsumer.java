package com.direct.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 
 * @author cck
 */
@Component
public class MyConsumer {
    
    //监听器监听指定的Queue
    @RabbitListener(queues="${myQueueName}")    
    public void process(String str) {
        
        System.out.println("Receive:"+str);
    }
}
