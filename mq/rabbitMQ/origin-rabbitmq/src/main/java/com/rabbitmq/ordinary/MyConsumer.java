package com.rabbitmq.ordinary;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * 消息消费者
 * @author cck
 */
public class MyConsumer {

    public static void main(String[] argv) throws Exception {
        
        // 创建连接工厂
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
        // 声明队列
        String queueName = channel.queueDeclare().getQueue();
        // 绑定队列
        String routingKey = "routingKey";
        channel.queueBind(queueName, exchangeName, routingKey);
        
        System.out.println(" --consumer start-- ");
        
        // 是否自动发送确认信息
        boolean autoAck = false;
        String consumerTag = "";
        while(true) {
            channel.basicConsume(queueName, autoAck, consumerTag, 
                    new DefaultConsumer(channel) {
                
                @Override
                public void handleDelivery(String consumerTag,
                                           Envelope envelope,
                                           AMQP.BasicProperties properties,
                                           byte[] body) throws IOException {
                    // 拿到路由键
                    String routingKey = envelope.getRoutingKey();
                    
                    // 获取消息
                    String bodyStr = new String(body, "UTF-8");
                    System.out.println(routingKey + ":" + bodyStr);
                    
                    // 确认消息
                    long deliveryTag = envelope.getDeliveryTag();
                    channel.basicAck(deliveryTag, false);
                }
            });
        }
    }
}
