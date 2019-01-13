package com.activemq.topic.producer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSTopicProducer {
    
    public static void main(String[] args) {
        
        // 连接工厂
        ConnectionFactory connectionFactory 
            = new ActiveMQConnectionFactory("tcp://119.29.240.120:61616");
        
        // 连接
        Connection connection = null;
        
        try {
            
            connection = connectionFactory.createConnection();
            connection.start();
            
            // 会话 参数一 true 表示这是一个事务性会话 false表示不是
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            
            // 目的地，这里是主题的方式
            Destination topic = session.createTopic("myTopic");
            
            // 创建一个生产者
            MessageProducer producer = session.createProducer(topic);
            
            // 创建一个消息
            TextMessage message = session.createTextMessage("Hello World");
            
            // 发送消息
            producer.send(message);
            
            session.commit();
            session.close();
            
            System.out.println("发送结束");
            
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
