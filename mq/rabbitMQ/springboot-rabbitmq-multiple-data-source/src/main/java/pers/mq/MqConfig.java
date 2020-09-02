package pers.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MqConfig {

    private Logger LOGGER = LoggerFactory.getLogger(MqConfig.class);

    @Primary
    @Bean(name = "rabbitMqConnectionFactoryOne")
    public CachingConnectionFactory rabbitMqConnectionFactoryOne(
            @Value("${spring.rabbitmq.one.host}") String host,
            @Value("${spring.rabbitmq.one.port}") Integer port,
            @Value("${spring.rabbitmq.one.username}") String username,
            @Value("${spring.rabbitmq.one.password}") String password,
            @Value("${spring.rabbitmq.one.publisher-confirms}") Boolean publisherConfirms,
            @Value("${spring.rabbitmq.one.publisher-returns}") Boolean publisherReturns){

        CachingConnectionFactory connectionFactory = buildCachingConnectionFactory(
                host, port, username, password, publisherConfirms, publisherReturns);

        return connectionFactory;
    }

    @Primary
    @Bean
    public RabbitTemplate rabbitTemplateOne(
            @Qualifier("rabbitMqConnectionFactoryOne") ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置序列化方式
        //rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);
        //确认推到mq服务器
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause)
                -> LOGGER.info("confirm callback id:{},ack:{},cause:{}", correlationData, ack, cause));
        //确认推到指定的队列
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey)
                -> LOGGER.info("return callback message：{},code:{},text:{}", message, replyCode, replyText));
        return rabbitTemplate;
    }

    @Bean(name = "rabbitMqConnectionFactoryTwo")
    public CachingConnectionFactory rabbitMqConnectionFactoryTwo(
            @Value("${spring.rabbitmq.two.host}") String host,
            @Value("${spring.rabbitmq.two.port}") Integer port,
            @Value("${spring.rabbitmq.two.username}") String username,
            @Value("${spring.rabbitmq.two.password}") String password,
            @Value("${spring.rabbitmq.two.publisher-confirms}") Boolean publisherConfirms,
            @Value("${spring.rabbitmq.two.publisher-returns}") Boolean publisherReturns){

        CachingConnectionFactory connectionFactory = buildCachingConnectionFactory(
                host, port, username, password, publisherConfirms, publisherReturns);

        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplateTwo(
            @Qualifier("rabbitMqConnectionFactoryTwo") ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        return rabbitTemplate;
    }

    private CachingConnectionFactory buildCachingConnectionFactory(
            String host, Integer port,String username,
            String password,Boolean publisherConfirms, Boolean publisherReturns) {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);
        connectionFactory.setChannelCacheSize(4);
        connectionFactory.setChannelCheckoutTimeout(0);
        connectionFactory.setPublisherConfirms(publisherConfirms);
        connectionFactory.setPublisherReturns(publisherReturns);
        return connectionFactory;
    }

}
