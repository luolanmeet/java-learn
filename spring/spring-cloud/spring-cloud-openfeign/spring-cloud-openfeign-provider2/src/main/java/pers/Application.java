package pers;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder().web(WebApplicationType.SERVLET)
                .sources(Application.class)
                .run(args);
    }

}
