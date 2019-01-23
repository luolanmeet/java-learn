package com.rabbitmq.ttl;

import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TTLQueueDemo {
    
    
    public static void main(String[] args) throws Exception {
        
        String QUEUE_NAME = "TTL_QUEUE";
        
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        Map<String, Object> arguments = new HashMap<>();
        // 设置队列的生存时间
        arguments.put("x-expires", 6000);
        // 使用默认的交换器
        channel.queueDeclare(QUEUE_NAME, false, false, false, arguments);
        
        channel.close();
        connection.close();
        factory.clone();
    }
    
}
