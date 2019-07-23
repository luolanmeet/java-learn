package pers.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * 
 * @author cck
 */
@RestController
public class HelloWorldController {

    private final static Random random = new Random();

    @HystrixCommand(
            fallbackMethod = "errorContent", // 调用失败时执行的方法
            commandProperties = {
                    // Hystrix 配置信息wiki：https://github.com/Netflix/Hystrix/wiki/Configuration
                    // 响应超过100ms，则进行熔断
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100")
            }
    )
    @RequestMapping("hello")
    public String helloWorld() throws InterruptedException {

        int time = random.nextInt(200);
        Thread.sleep(time);

        System.out.println("new time:" + time);

        return "Hello World";
    }

    public String errorContent() {
        return "Failut";
    }

}
