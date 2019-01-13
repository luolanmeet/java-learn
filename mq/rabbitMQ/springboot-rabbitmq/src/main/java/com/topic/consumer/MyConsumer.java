package com.topic.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.topic.MyConfiguration;

/**
 * 
 * @author cck
 */
@Component
public class MyConsumer {
    
    //监听器监听指定的Queue
    @RabbitListener(queues=MyConfiguration.QUEUE_NAME_A)    
    public void processA(String str) {
        
        System.out.println("Receive From queueA : "+str);
    }
    
    //监听器监听指定的Queue
    @RabbitListener(queues=MyConfiguration.QUEUE_NAME_B)    
    public void processB(String str) {
        
        System.out.println("Receive From queueB : "+str);
    }
}
