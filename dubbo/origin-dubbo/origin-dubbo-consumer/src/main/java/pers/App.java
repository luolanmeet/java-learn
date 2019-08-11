package pers;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext classPathXmlApplicationContext=
                new ClassPathXmlApplicationContext(new String[]{"application.xml"});

        // 直接通过指定 协议 ip 和 端口获取的服务
        IUserService userService = (IUserService) classPathXmlApplicationContext.
                getBean("userService");
        System.out.println(userService.sayHello("cck"));

        // 通过注册中心zk获取的服务
        IUserService userService2 = (IUserService) classPathXmlApplicationContext.
                getBean("userService2");
        System.out.println(userService2.sayHello("cck2"));

        // 访问 http://localhost:8080/user/say/cck
        // 发布服务的时候，除了dubbo协议，还发布了rest协议

    }

}
