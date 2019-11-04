package pers.demo.five;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * tx标签和@Transactional注解
 */
public class Main {
    
    public static void main(String[] args) {
    
        ClassPathXmlApplicationContext applicationContext
                = new ClassPathXmlApplicationContext("springDemoFive.xml");
    
        UserService userService = applicationContext.getBean(UserService.class);
        userService.saveOne();
        userService.saveTwo();
    }

}
