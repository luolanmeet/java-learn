package pers.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.common._interface.MqService;
import pers.common.model.SendMsgReq;
import pers.common.model.SendMsgResp;

import java.util.Map;

/**
 * 通用MQ使用入口
 * @auther ken.ck
 * @date 2022/7/2 15:01
 */
@Component
public class CommonMqService {

    @Autowired(required = false)
    private Map<String, MqService> mqServiceMap;

    public SendMsgResp sendMsg(SendMsgReq req) {
        MqService mqService = mqServiceMap.get(req.getMqType());
        if (mqService == null) {
            throw new RuntimeException("no support mq type [" + req.getMqType() + "]");
        }
        return mqService.sendMsg(req);
    }

}
