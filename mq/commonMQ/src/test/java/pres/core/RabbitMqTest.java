package pres.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pers.Application;
import pers.common.enums.MqType;
import pers.common.model.SendMsgReq;
import pers.core.CommonMqService;

/**
 * @auther ken.ck
 * @date 2022/7/2 18:16
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class RabbitMqTest {

    @Autowired
    private CommonMqService commonMqService;

    @Test
    public void testSendMsg() {
        SendMsgReq req = new SendMsgReq();
        req.mqType = MqType.RABBIT_MQ;
        commonMqService.sendMsg(req);
    }

}
