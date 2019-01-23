package com.rabbitmq.dlx;

import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
    
    public static void main(String[] args) throws Exception {
        
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        // 死信交换机名
        String DLX_EXCHANGE_NAME = "DLX_EXCHANGE";
        
        Map<String,Object> arguments = new HashMap<String,Object>();
        arguments.put("x-dead-letter-exchange", DLX_EXCHANGE_NAME); // 为队列指定死信交换机
        arguments.put("x-message-ttl", 6000); // 让消息过期，进入死信队列
        channel.queueDeclare("TEST_DLX_QUEUE", false, false, false, arguments);
        
        String msg = "Hello World";
        channel.basicPublish("", "TEST_DLX_QUEUE", null, msg.getBytes());
        
        channel.close();
        connection.close();
        factory.clone();
    }
    
}
