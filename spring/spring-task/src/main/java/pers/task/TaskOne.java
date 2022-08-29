package pers.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @auther ken.ck
 * @date 2022/8/29 20:45
 */
@Component
public class TaskOne {

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void sayHi() {
        System.out.println("hi, from " + Thread.currentThread().getName());
    }

}
