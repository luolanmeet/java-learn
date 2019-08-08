package pers.autoconfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * 其他项目引用此项目时，可以通过application.properties来配置此项目
 * 其实就是获取application.properties中的内容而已
 * @author cck
 */
@ConfigurationProperties(prefix = MyProperties.MY_FORMAT_PREFIX)
public class MyProperties {
    
    public static final String MY_FORMAT_PREFIX = "my.format";

    private Map<String, Object> info;
    
    public Map<String, Object> getInfo() {
        return info;
    }
    
    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }
}
