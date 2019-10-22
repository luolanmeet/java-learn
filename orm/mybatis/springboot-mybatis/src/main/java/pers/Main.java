package pers;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pers.mapper.IUserMapper;

@MapperScan("pers.mapper")
@SpringBootApplication
public class Main {
    
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Main.class, args);
        IUserMapper userMapper = applicationContext.getBean(IUserMapper.class);
        System.out.println(userMapper.findById(1));
    }

}
