package pers.common.vo;

import lombok.Data;

/**
 * @auther ken.ck
 * @date 2022/7/11 15:12
 */
@Data
public class MqInstanceMsg {

    private String mqType;

    private String host;

    private String port;

    private String username;

    private String password;

    private String producerGroup;

}
