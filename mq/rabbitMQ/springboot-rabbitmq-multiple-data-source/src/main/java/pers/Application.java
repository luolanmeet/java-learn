package pers;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application {
    
    public static void main(String[] args) {

        new SpringApplicationBuilder().sources(Application.class)
                .web(WebApplicationType.SERVLET).build()
                .run(args);
    }

}
