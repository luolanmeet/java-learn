package com.kafka.consumer;

import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConsumerDemo extends Thread {
    
    private final KafkaConsumer<Integer, String> kafkaConsumer;
    
    public ConsumerDemo(String topic) {
        
        // 配置 支持Properties和Map配置
        Properties properties = new Properties();
        
        // 设置broker地址。有多个则用 , 分隔
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "119.29.240.120:9092");
        // 设置组id
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "ConsumerDemo");
        // 设置自动提交
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        // 设置自动提交时间
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000");
        // 设置 KEY 的反序列化方式
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.IntegerDeserializer");
        // 设置 VALUE 的反序列化方式
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        // 设置消费消息的模式，earliest从最早的开始消费
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        
        kafkaConsumer = new KafkaConsumer<Integer, String>(properties);
        
        // 订阅
        kafkaConsumer.subscribe(Collections.singleton(topic));
    }
    
    @Override
    public void run() {
        
        while (true) {
            
            ConsumerRecords<Integer, String> consumerRecords = kafkaConsumer.poll(1000);
            
            for (ConsumerRecord<Integer, String> record : consumerRecords) {
                System.out.println("message receive: " + record.value());
            }
        }
    }
    
    public static void main(String[] args) {
        new ConsumerDemo("test").start();
    }
    
}
