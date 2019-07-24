package pers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

/**
 *
 * @author cck
 */
//@EnableHystrix // 启用Hystrix，这个是netflix的方式
@EnableCircuitBreaker // 用于启用Hystrix， 这个是还有启动hystrix.stream的功能
@SpringBootApplication
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
}
