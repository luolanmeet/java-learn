package pers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 不做配置的话，默认是单线程
 * @auther ken.ck
 * @date 2022/8/29 20:48
 */
@Configuration
public class ScheduleConfig {

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        // 设置线程池大小
        taskScheduler.setPoolSize(2);
        // 设置线程名前缀
        taskScheduler.setThreadNamePrefix("scheduling-");
        return taskScheduler;
    }

}
