package pers.consumer;

import pers.api.IRpcHelloService;
import pers.consumer.proxy.RpcProxy;

public class RpcConsumer {

    public static void main(String[] args) {

        IRpcHelloService helloService = RpcProxy.create(IRpcHelloService.class);
        System.out.println(helloService.hello("cck"));
    }

}
