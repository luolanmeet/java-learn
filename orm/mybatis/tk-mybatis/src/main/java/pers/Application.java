package pers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pers.bean.User;
import pers.mapper.IUserMapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan(basePackages = {"pers.mapper"})
@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        IUserMapper userMapper = applicationContext.getBean(IUserMapper.class);
        // 使用提供的api
        System.out.println(userMapper.selectByPrimaryKey(1));

        // 自定义查询
        Sqls sqls = Sqls.custom()
                .andEqualTo("userId", 1)
                .andEqualTo("name", "cck");
        Example example = Example.builder(User.class).andWhere(sqls).build();
        List<User> users = userMapper.selectByExample(example);
        System.out.println(users);
    }

}
