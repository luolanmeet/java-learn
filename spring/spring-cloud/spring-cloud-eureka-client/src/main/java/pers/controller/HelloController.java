package pers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 暴露服务
 * @author cck
 */
@RestController
public class HelloController {
    
    @Autowired
    Registration client;
    
    @RequestMapping("/hello")
    public String hello() {
        
        System.out.println(client.getServiceId());
        
        return "hello-service";
    }
    
}
