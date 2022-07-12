package pers.core.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * mq 消息基础类
 * @auther ken.ck
 * @date 2022/7/12 17:45
 */
@Data
@AllArgsConstructor
public class BaseMsgBody {

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 消息内容
     */
    private String body;

}
