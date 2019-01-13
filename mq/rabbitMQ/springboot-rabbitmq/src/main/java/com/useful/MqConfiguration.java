package com.useful;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * 定义消费者，由消费者创建队列
 * @author cck
 */
@EnableRabbit
@Configuration
public class MqConfiguration implements InitializingBean {

    @Autowired
    private AmqpAdmin amqpAdmin;
    
    @Autowired
    private ConnectionFactory cf;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        
        // 与生产者约定好交换器名
        FanoutExchange exchange = new FanoutExchange("cck.fanout");
        // 声明了交换器
        amqpAdmin.declareExchange(exchange);
        
        // 声明一个队列
        Queue queue = amqpAdmin.declareQueue();
        
        // 把队列绑定到交换器上
        amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange));
        
        // 监听队列消息!! 先定一个适配器
        MessageListenerAdapter adapter = new MessageListenerAdapter(new Object() {
            
            @SuppressWarnings("unused")
            public void handleMessage(Object msg) {
                System.out.println(msg);
            }
        });
        
        // 定义一个监听容器
        SimpleMessageListenerContainer listenerContainer = 
                new SimpleMessageListenerContainer(cf);
        // 设置对消息的处理
        listenerContainer.setMessageListener(adapter);
        // 设置监听的队列名字, 由于队列是消费者定义的，可以直接知道队列名字
        listenerContainer.setQueueNames(queue.getName());
        // 开始监听
        listenerContainer.start();
    }
}
