package per.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import per.service.ServiceTwo;

@Configuration
public class ConfigTwo {

    @Bean
    public ServiceTwo serviceTwo() {
        System.out.println("ConfigTwo");
        return new ServiceTwo();
    }

}
