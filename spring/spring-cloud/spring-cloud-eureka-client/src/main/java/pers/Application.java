package pers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 使用 EnableDiscoveryClient 
 * 让服务使用Eureka服务器
 * 实现服务注册和发现
 * 服务提供方
 * @author cck
 */
@EnableDiscoveryClient
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        
        SpringApplication.run(Application.class, args);
        
    }
    
}
