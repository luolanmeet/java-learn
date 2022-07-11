package pers.common.vo;

import lombok.Data;

/**
 * @auther ken.ck
 * @date 2022/7/11 14:44
 */
@Data
public class PublishMsgReq extends MqInstanceMsg {

    private String topicName;

    private String message;

}
