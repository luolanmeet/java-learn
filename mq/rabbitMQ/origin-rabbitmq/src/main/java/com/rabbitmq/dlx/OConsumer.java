package com.rabbitmq.dlx;

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

public class OConsumer {
    
    public static void main(String[] args) throws Exception {
        
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        // 死信交换机名
        String DLX_EXCHANGE_NAME = "DLX_EXCHANGE";
        // 测试的队列名
        String TEST_DLX_QUEUE_NAME = "TEST_DLX_QUEUE";
        
        Map<String,Object> arguments = new HashMap<String,Object>();
        arguments.put("x-dead-letter-exchange", DLX_EXCHANGE_NAME); // 为队列指定死信交换机
        arguments.put("x-message-ttl", 6000); // 让消息过期，进入死信队列
        arguments.put("x-max-length", 10);    // 超过队列长度，先入队的消息也会进入死信
        channel.queueDeclare(TEST_DLX_QUEUE_NAME, false, false, false, arguments);
        
        Consumer consumer = new DefaultConsumer(channel) {
            
            @Override
            public void handleDelivery(
                    String consumerTag, 
                    Envelope envelope, 
                    BasicProperties properties, 
                    byte[] body) throws IOException {
                
                super.handleDelivery(consumerTag, envelope, properties, body);
                
                String msg = new String(body, "UTF-8");
                System.out.println("received and reject msg : " + msg);
                
                // 拒绝消息
                channel.basicReject(envelope.getDeliveryTag(), false);
                // 批量拒绝
                //channel.basicNack(envelope.getDeliveryTag(), true, false);
            }
        };
        
        System.out.println(" --consumer start-- ");
        
        channel.basicConsume(TEST_DLX_QUEUE_NAME, consumer);
        System.in.read();
        channel.close();
        connection.close();
        factory.clone();
    }
    
}
