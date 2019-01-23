http://www.rabbitmq.com/ttl.html
# 测试RabbitMQ的TTL功能 TTL time to live

## 消息的生存时间（超时变死信）
有两种过期设置（均有设置则按照时间小的）

1.队列级别，声明队列时加入设置，所有进入该队列的消息都遵循这个时间规则

    Map<String, Object> arguments = new HashMap<>();
    arguments.put("x-message-ttl", 6000);  // 6s后变死信
    channel.queueDeclare(QUEUE_NAME, false, false, false, arguments);
    
2.消息级别

    AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .expiration("15000")  // 15s后变死信
                .build();
    channel.basicPublish("", "TTL_QUEUE", properties, msg.getBytes());
    
## 队列的生存时间（超时队列会被删除）

    Map<String, Object> arguments = new HashMap<>();
    arguments.put("x-expires", 6000);  // 队列在没有消费者的情况下，6s后自动删除队列
    channel.queueDeclare(QUEUE_NAME, false, false, false, arguments);