package pers.common.model;

import lombok.Data;

/**
 * 发送消息请求类
 * @auther ken.ck
 * @date 2022/7/2 14:55
 */
@Data
public class SendMsgReq {

    private String mqType;

    private String exchangeName;

    private String topicName;

    private String tag;

    private String queueName;

    private String routingKey;

    private String message;

}
