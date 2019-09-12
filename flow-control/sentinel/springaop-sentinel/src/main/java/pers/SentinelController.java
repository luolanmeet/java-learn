package pers;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class SentinelController {

    @SentinelResource(value = "sayHello") //针对方法级别的限流
    @RequestMapping("sayHello")
    public String sayHello() {
        return "hello " + LocalDateTime.now();
    }

}
