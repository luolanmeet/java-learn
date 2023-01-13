package pers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @author cck
 */
@SpringBootApplication
public class ConsumerApp {
    
    public static void main(String[] args) {
        // http://localhost:8080/hello/cck
        SpringApplication.run(ConsumerApp.class, args);
    }
    
}
