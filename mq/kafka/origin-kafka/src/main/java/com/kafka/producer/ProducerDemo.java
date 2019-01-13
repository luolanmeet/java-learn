package com.kafka.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProducerDemo extends Thread {
    
    private final KafkaProducer<Integer,String> producer;

    private final String topic;

    public ProducerDemo(String topic){
        
        this.topic=topic;
        
        Properties properties=new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "119.29.240.120:9092");
        properties.put(ProducerConfig.CLIENT_ID_CONFIG,"ProducerDemo");
        properties.put(ProducerConfig.ACKS_CONFIG,"-1");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.IntegerSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        
        producer = new KafkaProducer<Integer, String>(properties);
    }

    @Override
    public void run() {
        
        int num = 0;
        
        while(num < 50){
            
            String message="message_" + num++;
            System.out.println("begin send message:"+message);
            
            producer.send(new ProducerRecord<Integer, String>(topic, message));
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ProducerDemo("test").start();
    }
}
