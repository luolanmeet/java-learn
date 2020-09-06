package pers.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ListenerDemo {

    private static final String LISTENER_ONE_ID = "ListenerOneId";

    private static final String LISTENER_TWO_ID = "ListenerTwoId";

    @Resource
    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;

    @RabbitListener(queues = "${rabbitmq1.queue.name}",
            id = LISTENER_ONE_ID, autoStartup = "false",
            containerFactory = "containerFactoryOne")
    public void handleOne(Message message) {
        System.out.println(new String(message.getBody()));
    }

    /* TODO 这里是为默认的MQ服务自动创建的交换器和队列， 不受containerFactory影响 如何指定使用的MQ服务？*/
    @RabbitListener(
            bindings = @QueueBinding(
                    exchange = @Exchange("${rabbitmq2.exchange.name}"),
                    value = @Queue("${rabbitmq2.queue.name}"),
                    key = {"${rabbitmq2.routing.key}"}),
            id = LISTENER_TWO_ID, autoStartup = "false",
            containerFactory = "containerFactoryTwo")
    public void handleTwo(Message message) {
        System.out.println(new String(message.getBody()));
    }

    /** 是否开启监听 */
    private volatile Boolean isStarted = false;

    /**
     * 启动mq监听
     */
    public void startListen() {
        if (isStarted) {
            return ;
        }
        synchronized (this) {
            if (isStarted) {
                return;
            }
            MessageListenerContainer container =
                    rabbitListenerEndpointRegistry.getListenerContainer(LISTENER_TWO_ID);
            container.start();
            isStarted = true;
        }
    }

}
