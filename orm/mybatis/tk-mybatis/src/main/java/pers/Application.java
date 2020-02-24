package pers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pers.mapper.IUserMapper;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = {"pers.mapper"})
@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        IUserMapper userMapper = applicationContext.getBean(IUserMapper.class);
        System.out.println(userMapper.selectByPrimaryKey(1));
    }

}
