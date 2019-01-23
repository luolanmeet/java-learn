package com.rabbitmq.ttl;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TTLMsgProducer {
    
    public static void main(String[] args) throws Exception {
        
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        System.out.println(" --producer send msg-- ");
        
        // 消息级别的生存时间设置
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .expiration("15000")
                .build();

        String msg = "Hello World";
        channel.basicPublish("", "TTL_MSG_QUEUE", properties, msg.getBytes());
        
        channel.close();
        connection.close();
        factory.clone();
    }
    
}
