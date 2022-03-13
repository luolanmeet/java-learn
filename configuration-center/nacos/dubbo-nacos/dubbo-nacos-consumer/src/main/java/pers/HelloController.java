package pers;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Reference(timeout = 5000)
    private IHelloService helloService;

    @RequestMapping("sayHello")
    public String sayHello(String name) {
        return helloService.sayHello(name);
    }

}
