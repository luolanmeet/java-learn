package pers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 *
 * @author cck
 */
@EnableHystrixDashboard // 用于启用Hystrix 的Dashborad
@SpringBootApplication
public class Application {
    
    // 访问 http://localhost:9090/hystrix
    // 然后填入监视的服务的url http://localhost:8080/actuator/hystrix.stream
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
}
