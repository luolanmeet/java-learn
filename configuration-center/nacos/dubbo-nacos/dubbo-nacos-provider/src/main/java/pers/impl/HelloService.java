package pers.impl;

import org.apache.dubbo.config.annotation.Service;
import pers.IHelloService;

@Service
public class HelloService implements IHelloService {

    @Override
    public String sayHello(String str) {
        return "hello " + str;
    }

}
