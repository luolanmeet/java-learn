package pers.service;

import org.springframework.web.bind.annotation.RestController;
import pers.IHelloWorldService;

/**
 * 实现接口的话，则不需要再写XxxMapping
 * @author cck
 */
@RestController
public class HelloWorldService implements IHelloWorldService {

    @Override
    public String helloWorld() {
        return "hello world";
    }
    
}
