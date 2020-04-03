package per.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import per.service.ServiceOne;

@Configuration
public class ConfigOne {

    @Bean
    public ServiceOne serviceOne() {
        System.out.println("ConfigOne");
        return new ServiceOne();
    }

}
