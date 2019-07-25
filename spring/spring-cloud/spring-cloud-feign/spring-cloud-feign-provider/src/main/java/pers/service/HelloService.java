package pers.service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 不要求实现接口，只要 hello-service/hello 能对应上即可调用
 * @author cck
 */
@RestController
public class HelloService {
    
    @PostMapping("hello")
    public String hello(@RequestBody String name) {
        return "hello " + name;
    }
}
