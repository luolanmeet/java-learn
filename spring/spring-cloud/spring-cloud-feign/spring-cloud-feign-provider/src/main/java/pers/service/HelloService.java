package pers.service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * hello-service/hello
 * @author cck
 */
@RestController
public class HelloService {
    
    @PostMapping("hello")
    public String hello(@RequestBody String name) {
        return "hello " + name;
    }
}
