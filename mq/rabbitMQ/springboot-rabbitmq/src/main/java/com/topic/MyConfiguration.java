package com.topic;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author cck
 */
@Configuration
public class MyConfiguration {
    
    public final static String QUEUE_NAME_A = "queueA";
    public final static String QUEUE_NAME_B = "queueB";
    public final static String EXCHANGE_NAME = "myExchange";
    
    /**
     * 声明了两个队列
     * @return
     */
    @Bean(name = "queueA")
    public Queue queueA() {
        return new Queue(QUEUE_NAME_A);
    }
    @Bean(name = "queueB")
    public Queue queueB() {
        return new Queue(QUEUE_NAME_B);
    }
    
    /**
     * 声明一个topic类型的交换器
     */
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }
    
    /**
     * 把队列与交换器绑定
     * 队列 A的路由规则是 topic.queueA
     * 队列 B的路由规则是 topic.queue#    *表示一个单词，#表示零或多个单词 
     */
    @Bean
    Binding bindindExchangeMessageA(
            @Qualifier("queueA") Queue queue, 
            TopicExchange exchange) {
        
        String routingKey = "topic.queueA";
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }
    @Bean
    Binding bindindExchangeMessageB(
            @Qualifier("queueB") Queue queue, 
            TopicExchange exchange) {
        
        String routingKey = "topic.#";
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }
    
}
