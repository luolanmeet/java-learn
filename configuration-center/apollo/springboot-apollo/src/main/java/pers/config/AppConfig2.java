package pers.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class AppConfig2 {

    /**
     * 测试 properties的配置和apollo的优先级
     * apollo优先级高
     */
    @Value("${timeout:0}")
    private Integer timeout;

    /**
     * 测试正常的properties注入
     */
    @Value("${timeout2:0}")
    private Integer timeout2;

}
