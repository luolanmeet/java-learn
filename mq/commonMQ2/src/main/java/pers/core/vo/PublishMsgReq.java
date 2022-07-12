package pers.core.vo;

import lombok.Data;

/**
 * 推送消息请求类
 * @auther ken.ck
 * @date 2022/7/11 14:44
 */
@Data
public class PublishMsgReq extends MqInstanceMsg {

    /**
     * 生产者组
     */
    private String producerGroup;

    /**
     * 消息 topic
     */
    private String topic;

    /**
     * 消息标签
     */
    private String tag;

    /**
     * 消息体
     */
    private BaseMsgBody message;

}
