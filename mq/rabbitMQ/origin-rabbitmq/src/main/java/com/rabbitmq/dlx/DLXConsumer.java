package com.rabbitmq.dlx;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class DLXConsumer {
    
    public static void main(String[] args) throws Exception {
        
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        // 死信交换机名
        String DLX_EXCHANGE_NAME = "DLX_EXCHANGE";
        // 死信队列名
        String DLX_QUEUE_NAME = "DLX_QUEUE";
        
        // 声明死信交换机
        channel.exchangeDeclare(DLX_EXCHANGE_NAME, "topic", false, false, false, null);
        // 声明死信队列
        channel.queueDeclare(DLX_QUEUE_NAME, false, false, false, null);
        // 绑定死信队列和死信交换机
        channel.queueBind(DLX_QUEUE_NAME, DLX_EXCHANGE_NAME, "#");
        
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
        channel.basicConsume(DLX_QUEUE_NAME, true, consumer);
        
        System.in.read();
        channel.close();
        connection.close();
        factory.clone();
    }

}
