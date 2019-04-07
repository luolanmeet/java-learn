package pers.demo2;

import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 自动刷新
 * @author cck
 */
@EnableScheduling
@SpringBootApplication
public class Application {

    private final ContextRefresher contextRefresher;
    
    public Application(ContextRefresher contextRefresher) {
        this.contextRefresher = contextRefresher;
    }
    
    @Scheduled(fixedDelay = 5 * 1000, initialDelay = 3 * 1000)
    public void autoRefresh() {
        
        Set<String> refresh = contextRefresher.refresh();
        
        System.out.println("=========" + refresh + "==========");
        
        if (!refresh.isEmpty()) {
            System.out.printf("当前配置已刷新.  %s", refresh);
        }
    }
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
}
