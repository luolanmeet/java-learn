package pers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @author cck
 */
@SpringBootApplication
public class App {
    
    public static void main(String[] args) {
        // http://localhost:8080/hello/cck
        SpringApplication.run(App.class, args);
    }
    
}
