package pers.autoconfiguration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pers.MyFormatTemplate;
import pers.format.FormatProcessor;

/**
 * 导入实现类，获取application.properties中配置
 * 构造template实例
 * @author cck
 */
@Import(FormatAutoConfiguration.class)
@Configuration
@EnableConfigurationProperties(MyProperties.class)
public class MyAutoConfiguration {

    @Bean
    public MyFormatTemplate myFormatTemplate(MyProperties myProperties, FormatProcessor formatProcessor) {
        
        // 这里拿到 myProperties，可以根据配置信息做相应的处理
        
        return new MyFormatTemplate(myProperties, formatProcessor);
    }
    
}
