package pers.core.vo;

import lombok.Data;

/**
 * mq 实例信息
 * @auther ken.ck
 * @date 2022/7/11 15:12
 */
@Data
public class MqInstanceMsg {

    /**
     * mq 类型
     */
    private String mqType;

    /**
     * mq 服务 host
     */
    private String host;
    /**
     * mq 服务端口
     */
    private String port;

}
