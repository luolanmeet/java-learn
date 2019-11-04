package pers.demo.two;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    
    public static void main(String[] args) {
        
        // 在这篇文章看的 https://www.jianshu.com/p/ebaefa1b0764
        
        ClassPathXmlApplicationContext applicationContext
                = new ClassPathXmlApplicationContext("springDemoTwo.xml");
        
        Cat cat = applicationContext.getBean(Cat.class);
        
        cat.sayHi("cck");
        System.out.println();
        cat.sayHello();
    }

}
