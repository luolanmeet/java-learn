package pers.common._interface;

import pers.common.model.SendMsgReq;
import pers.common.model.SendMsgResp;

/**
 * @auther ken.ck
 * @date 2022/7/2 14:36
 */
public interface MqService {

    /**
     * 发送 MQ 消息
     * @param req
     * @return
     */
    SendMsgResp sendMsg(SendMsgReq req);

}
