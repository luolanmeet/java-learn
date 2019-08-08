package pers.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import pers.format.FormatProcessor;
import pers.format.JsonFormatProcessor;
import pers.format.StringFormatProcessor;

/**
 * 初始化实现类
 * @author cck
 */
@Configuration
public class FormatAutoConfiguration {

    @Bean
    @Primary
    @ConditionalOnMissingClass("com.alibaba.fastjson.JSON") // 当类路径下没有这个类时，使用这个实现
    public FormatProcessor stringFormatProcessor() {
        return new StringFormatProcessor();
    }

    @Bean
    @ConditionalOnClass(name = "com.alibaba.fastjson.JSON")
    public FormatProcessor jsonFormatProcessor() {
        return new JsonFormatProcessor();
    }
    
}
