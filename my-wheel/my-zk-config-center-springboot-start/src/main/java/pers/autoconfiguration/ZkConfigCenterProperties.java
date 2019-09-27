package pers.autoconfiguration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * 可以通过application.properties 对zk-config-center进行配置
 * @author cck
 */
@Slf4j
@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = ZkConfigCenterProperties.PREFIX)
public class ZkConfigCenterProperties implements InitializingBean {
    
    final static String PREFIX = "zk.config.center";
    
    // zk地址默认值
    private final static String ZK_ADDRESS = "127.0.0.1";
    private String address = ZK_ADDRESS;
    
    // zk端口默认值
    private final static String ZK_PORT = "2181";
    private String port = ZK_PORT;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("\n zk config center [{}] \n", this);
    }
    
}
