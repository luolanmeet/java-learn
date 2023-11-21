package pers;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @auther ken.ck
 * @date 2023/11/21 17:16
 */
@SpringBootApplication
public class EsApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(EsApplication.class).run(args);
    }

}
