package com.activemq.topic.consumer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 持久化订阅
 * 1. 连接设置ClientId
 * 2. 创建消费者时用 createDurableSubscriber 方法
 * @author cck
 */
public class JMSPersistentTopicConsumer {
    
    public static void main(String[] args) {
        
        // 连接工厂
        ConnectionFactory connectionFactory 
            = new ActiveMQConnectionFactory("tcp://119.29.240.120:61616");
        
        // 连接
        Connection connection = null;
        
        try {
            
            connection = connectionFactory.createConnection();
            connection.setClientID("cck-001");
            connection.start();
            
            // 会话 参数一 true 表示这是一个事务性会话 false表示不是
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            
            // 目的地，这里是主题的方式
            Topic topic = session.createTopic("myTopic");
            
            // 创建一个消费者  createDurableSubscriber , 表示持久化订阅
            MessageConsumer consumer = session.createDurableSubscriber(topic, "cck-001");
            
            // 接收消息，这里receive方法会阻塞 
            // 返回的数据，实际上需要做类型判断
            TextMessage message = (TextMessage) consumer.receive();
            
            System.out.println(message.getText());
             
            session.commit();
            session.close();
            
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
    
}
