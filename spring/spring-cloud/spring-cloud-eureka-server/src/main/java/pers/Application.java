package pers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka 服务注册中心
 * @author cck
 */
@EnableEurekaServer
@SpringBootApplication
public class Application {
    
    public static void main(String[] args) {
        
        SpringApplication.run(Application.class, args);
        
    }
    
}
