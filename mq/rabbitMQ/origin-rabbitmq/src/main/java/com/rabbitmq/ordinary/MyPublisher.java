package com.rabbitmq.ordinary;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 消息生产者 
 * @author cck
 */
public class MyPublisher {
    
    public static void main(String[] args) throws IOException, TimeoutException {
        
        // 创建连接的工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 默认guest，不设置也行
        factory.setUsername("guest");
        factory.setPassword("guest");
        // 设置RabbitMQ地址
        factory.setHost("localhost");
        // 建立到代理服务器的连接
        Connection connection = factory.newConnection();
        // 获取信道
        Channel channel = connection.createChannel();
        // 声明交换器
        String exchangeName = "exchange";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
        // 发布消息
        String routingKey = "routingKey";
        byte[] bytes = "hello".getBytes();
        channel.basicPublish(exchangeName, routingKey, null, bytes);
        
        channel.close();
        connection.close();
    }
}
