package pers;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @auther ken.ck
 * @date 2021/6/14 02:15
 */
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(Main.class)
                .build().run(args);
    }

}
