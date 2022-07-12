package pers.mq.rocketmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pers.Application;
import pers.common.constants.MqType;
import pers.core.client.CommonMqClient;
import pers.core.vo.BaseMsgBody;
import pers.core.vo.ConsumeMsgReq;
import pers.core.vo.PublishMsgReq;
import pers.core.vo.PublishMsgResp;

import java.util.concurrent.CountDownLatch;

/**
 * @auther ken.ck
 * @date 2022/7/2 18:16
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class RocketMqTest {

    @Autowired
    private CommonMqClient commonMqClient;

    @Test
    public void testSendMsg1() {

        PublishMsgReq req = new PublishMsgReq();
        req.setMqType(MqType.ROCKET_MQ);
        req.setHost("localhost");
        req.setPort("9876");
        req.setProducerGroup("my-producer-group");
        req.setTopic("hello-topic");
        req.setTag("tagA");
        BaseMsgBody msgBody = new BaseMsgBody("", "hello world " + System.currentTimeMillis());
        req.setMessage(msgBody);
        PublishMsgResp resp = commonMqClient.publishMsg(req);
        System.out.println(resp);
    }

    @Test
    public void testSendMsg2() {

        // 不同实例

        PublishMsgReq req = new PublishMsgReq();
        req.setMqType(MqType.ROCKET_MQ);
        req.setProducerGroup("my-producer-group");
        req.setTopic("hello-topic");
        req.setTag("tagA");

        req.setHost("localhost");
        req.setPort("9876");
        BaseMsgBody msgBody = new BaseMsgBody("", "hello world " + System.currentTimeMillis());
        req.setMessage(msgBody);
        PublishMsgResp resp = commonMqClient.publishMsg(req);
        System.out.println(resp);

        req.setHost("39.108.194.220");
        req.setPort("9876");
        msgBody = new BaseMsgBody("", "hello world " + System.currentTimeMillis());
        req.setMessage(msgBody);
        resp = commonMqClient.publishMsg(req);
        System.out.println(resp);
    }

    @Test
    public void testConsumeMsg1() throws InterruptedException {

        // 不同 group 相同 topic

        ConsumeMsgReq req = new ConsumeMsgReq();
        req.setMqType(MqType.ROCKET_MQ);
        req.setHost("localhost");
        req.setPort("9876");
        req.setTopic("hello-topic1");
        req.setTag("*");

        req.setConsumerGroup("my-group1");
        commonMqClient.consumeMsg(req, null);

        req.setConsumerGroup("my-group2");
        commonMqClient.consumeMsg(req, null);

        new CountDownLatch(1).await();
    }

    @Test
    public void testConsumeMsg2() throws InterruptedException {

        // 相同 group 不同 topic

        ConsumeMsgReq req = new ConsumeMsgReq();
        req.setMqType(MqType.ROCKET_MQ);
        req.setHost("localhost");
        req.setPort("9876");
        req.setConsumerGroup("my-group1");
        req.setTag("*");

        req.setTopic("hello-topic2");
        commonMqClient.consumeMsg(req, null);

        req.setTopic("hello-topic3");
        commonMqClient.consumeMsg(req, null);

        new CountDownLatch(1).await();
    }

}
