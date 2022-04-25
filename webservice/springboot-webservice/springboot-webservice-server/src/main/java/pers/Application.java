package pers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @auther ken.ck
 * @date 2022/4/25 19:43
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // http://localhost:8080/services/
        SpringApplication.run(Application.class, args);
    }

}
