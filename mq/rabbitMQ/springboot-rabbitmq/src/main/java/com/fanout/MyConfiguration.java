package com.fanout;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
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
    public final static String EXCHANGE_NAME = "myFanoutExchange";
    
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
     * 声明一个fanout类型的交换器
     * 发送到fanout类型的交换器上的消息会被广播到绑定在这个交换器上的所有队列
     */
    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }
    
    @Bean
    Binding bindindExchangeMessageA(
            @Qualifier("queueA") Queue queue, 
            FanoutExchange exchange) {
        
        return BindingBuilder.bind(queue).to(exchange);
    }
    @Bean
    Binding bindindExchangeMessageB(
            @Qualifier("queueB") Queue queue, 
            FanoutExchange exchange) {
        
        return BindingBuilder.bind(queue).to(exchange);
    }
    
}
