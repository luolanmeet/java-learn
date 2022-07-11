package pers.mq.rocketmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pers.Application;
import pers.common.client.CommonMqClient;
import pers.common.vo.PublishMsgReq;
import pers.constants.MqType;

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
    public void testSendMsg() {
        PublishMsgReq req = new PublishMsgReq();
        req.setMqType(MqType.ROCKET_MQ);
        req.setHost("localhost");
        req.setPort("9876");
        req.setProducerGroup("my-group2");

        req.setTopicName("hello-topic");
        req.setMessage("hello world222");
        commonMqClient.publishMsg(req);

        req.setMqType(MqType.ROCKET_MQ);
        req.setHost("39.108.194.220");
        req.setPort("9876");
        req.setProducerGroup("my-group2");

        req.setTopicName("hello-topic");
        req.setMessage("hello world222");
        commonMqClient.publishMsg(req);
    }

}
