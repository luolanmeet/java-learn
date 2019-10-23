package pers;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.bean.User;

public class Application {
    
    public static void main(String[] args) {
    
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        testFactoryBean(applicationContext);
    }
    
    private static void testFactoryBean(ClassPathXmlApplicationContext applicationContext) {
    
        // 拿到的是UserFactoryBean产生的User实例
        User user1 = (User) applicationContext.getBean("user");
        System.out.println(user1);
    
        // 如果UserFactoryBean中设置了单例，那么返回的是实例都是单例的
        User user2 = (User) applicationContext.getBean("user");
        user2.setName("ck");
        System.out.println(user1);
    
        // 加 & 拿到的是UserFactoryBean本身的实例
        Object userFactoryBean = applicationContext.getBean("&user");
        System.out.println(userFactoryBean);
    }
    
}
