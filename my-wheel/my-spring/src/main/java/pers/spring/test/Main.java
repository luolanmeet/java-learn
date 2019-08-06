package pers.spring.test;

import pers.spring.formework.context.ApplicationContext;
import pers.spring.test.obj.UserService;

public class Main {

    public static void main(String[] args) {

        ApplicationContext applicationContext =
                new ApplicationContext("classpath:application.properties");

        UserService userService = (UserService) applicationContext.getBean("userService");

        userService.sayHello("cck");

        System.out.println(userService.getUserDAO());
//        String scanPackage = "pers.spring.formework.beans";
//
//        String tmp = "/" + scanPackage.replaceAll("\\.", "/");
//
//        URL url = Main.class.getClassLoader().getResource(scanPackage.replaceAll("\\.", "/"));
//        System.out.println(tmp);
//        System.out.println(url);
    }

}
