package pers;

import pers.service.UserService;

import javax.xml.ws.Endpoint;

/**
 * @auther ken.ck
 * @date 2022/4/25 10:39
 */
public class Main {

    public static void main(String[] args) {

        // http://localhost:10001/helloWorld?wsdl

        // 将服务发布出去
        Endpoint.publish("http://localhost:10001/helloWorld", new UserService());
        System.out.println("服务端启动成功！");
    }

}
