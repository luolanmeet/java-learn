package pers.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author cck
 */
@RestController
public class HelloWorldService {
    
    @GetMapping("helloWorld")
    public String helloWorld() {
        return "hello world";
    }
    
}
