package com.rabbitmq.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
    
    public static void main(String[] args) throws Exception {
        
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        // 测试的队列名
        String TEST_DLX_QUEUE_NAME = "TEST_DLX_QUEUE";
        
        String msg = "Hello World";
        channel.basicPublish("", TEST_DLX_QUEUE_NAME, null, msg.getBytes());
        
        channel.close();
        connection.close();
        factory.clone();
    }
    
}
