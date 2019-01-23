package com.rabbitmq.ttl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class TTLMsgConsumer {
    
    
    public static void main(String[] args) throws Exception {
        
        String QUEUE_NAME = "TTL_MSG_QUEUE";
        
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        Map<String, Object> arguments = new HashMap<>();
        // 队列级别的生存时间设置。 超过6秒则消息变死信
        arguments.put("x-message-ttl", 6000);
        // 使用默认的交换器
        channel.queueDeclare(QUEUE_NAME, false, false, false, arguments);
        
        Consumer consumer = new DefaultConsumer(channel) {
            
            @Override
            public void handleDelivery(
                    String consumerTag, 
                    Envelope envelope, 
                    BasicProperties properties, 
                    byte[] body) throws IOException {
                
                super.handleDelivery(consumerTag, envelope, properties, body);
                
                String msg = new String(body, "UTF-8");
                System.out.println("received msg : " + msg);
            }
        };
     
        System.out.println(" --consumer start-- ");
        channel.basicConsume(QUEUE_NAME, true, consumer);
        
        System.in.read();
        
        channel.close();
        connection.close();
        factory.clone();
    }
    
}
