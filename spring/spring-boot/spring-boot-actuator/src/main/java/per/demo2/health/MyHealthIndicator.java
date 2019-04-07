package per.demo2.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.stereotype.Component;

/**
 * 
 * @author cck
 */
@Component
public class MyHealthIndicator extends AbstractHealthIndicator {
    
    /**
     * 在次检查是否符合自己定义的健康指标
     * CPU、内存、磁盘等
     */
    @Override
    protected void doHealthCheck(Builder builder) throws Exception {
        
//        builder.up().withDetail("MyHealthIndicator", "good");
        builder.down().withDetail("MyHealthIndicator", "bad");
    }

}
