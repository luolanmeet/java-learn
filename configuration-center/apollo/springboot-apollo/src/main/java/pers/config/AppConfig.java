package pers.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class AppConfig {

    @Value("${timeout:0}")
    private Integer timeout;

    @Value("${name:huahua}")
    private String name;

}
