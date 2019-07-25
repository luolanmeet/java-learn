package pers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.IHelloService;
import pers.IHelloWorldService;

/**
 * 
 * @author cck
 */
@RestController
public class TestController {
    
    @Autowired
    private IHelloService helloService;
    
    @Autowired
    private IHelloWorldService helloWorldService;
    
    @RequestMapping("testHello")
    public String hello(String name) {
        return helloService.hello(name);
    }
    
    @RequestMapping("testHelloWorld")
    public String hello() {
        return helloWorldService.helloWorld();
    }
    
}
