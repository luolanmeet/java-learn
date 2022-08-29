package pers.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @auther ken.ck
 * @date 2022/8/29 20:47
 */
@Component
public class TaskTwo {

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.SECONDS)
    public void sayHello() {
        System.out.println("hello, from " + Thread.currentThread().getName());
    }

}
