http://www.rabbitmq.com/dlx.html
# 测试死信队列
## 当发生以下情况时，队列中的消息将变成死信，消息将进入死信交换机
* 消息被拒绝 (basic.reject or basic.nack) with requeue=false
* 由于TTL机制，消息过期
* 超过队列长度（先入队的消息会变成死信）