package pers.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pers.annotation.ConfigItem;

/**
 *  属性字段要求不能为基本变量，因为基本变量有默认值
 *  （这个还是得在考虑下）
 * @author cck
 */
@Setter
@Getter
@ToString
public class DbUserConfig {

    @ConfigItem("host_name")
    private String hostName;
    
    @ConfigItem("port")
    private Integer port;

}
