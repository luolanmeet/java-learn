package pers;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.mapper.IUserMapper;

public class Main {
    
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        IUserMapper userMapper = applicationContext.getBean(IUserMapper.class);
        System.out.println(userMapper.findById(1));
    }

}
