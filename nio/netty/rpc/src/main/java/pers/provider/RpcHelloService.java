package pers.provider;

import pers.api.IRpcHelloService;

import java.util.concurrent.TimeUnit;

public class RpcHelloService implements IRpcHelloService {

    public String hello(String name) {

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "hello " + name + " !";
    }

}
