package pers;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @auther ken.ck
 * @date 2022/7/2 16:22
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(Application.class)
                .web(WebApplicationType.NONE).build()
                .run(args);
    }
}
