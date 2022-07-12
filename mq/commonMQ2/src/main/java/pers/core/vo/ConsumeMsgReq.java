package pers.core.vo;

import lombok.Data;

/**
 * 消费消息请求类
 * @auther ken.ck
 * @date 2022/7/11 14:44
 */
@Data
public class ConsumeMsgReq extends MqInstanceMsg {

    /**
     * 消费者组
     */
    private String consumerGroup;

    /**
     * 消息 topic
     */
    private String topic;

    /**
     * 消息标签
     */
    private String tag;

    /**
     * 业务类型
     */
    private String bizType;

}
