package pers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloConsumerController {
    
    @Autowired
    RestTemplate restTemplate;
    
    @RequestMapping("/hello-consumer")
    public String helloConsumer() {
        
        //调用hello-service服务，注意这里用的是服务名，而不是具体的ip+port
        String resp = restTemplate.getForObject("http://hello-server/hello", String.class);

        System.out.println("get resp: " + resp);
        
        return "hello consumer finish !!!";
    }
    
}
