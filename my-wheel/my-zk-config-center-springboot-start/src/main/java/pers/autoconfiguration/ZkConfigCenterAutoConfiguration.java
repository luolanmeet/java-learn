package pers.autoconfiguration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.ZkConfigCenterTemplate;

/**
 * 
 * @author cck
 */
@Configuration
@EnableConfigurationProperties(ZkConfigCenterProperties.class)
public class ZkConfigCenterAutoConfiguration {
    
    @Bean
    public ZkConfigCenterTemplate myFormatTemplate(ZkConfigCenterProperties properties) {
        return new ZkConfigCenterTemplate(properties);
    }
    
}
