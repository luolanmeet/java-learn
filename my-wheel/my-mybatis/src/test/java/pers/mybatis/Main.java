package pers.mybatis;

import pers.mybatis.mapper.Test;
import pers.mybatis.mapper.TestMapper;
import pers.mybatis.mapper.User;
import pers.mybatis.mapper.UserMapper;
import pers.mybatis.session.Configuration;
import pers.mybatis.session.SqlSession;
import pers.mybatis.session.SqlSessionFactory;
import pers.mybatis.session.SqlSessionFactoryBuilder;

public class Main {

    public static void main(String[] args) throws Exception {

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(new Configuration());
        SqlSession session = sqlSessionFactory.openSession();

        TestMapper testMapper = session.getMapper(TestMapper.class);
        Test test = testMapper.findByid(1);
        System.out.println(test);
        
        test = testMapper.findByIdAndName(1, "233");
        System.out.println(test);
        
        test = testMapper.findByIdAndNums(2, 111);
        System.out.println(test);
        
        UserMapper userMapper = session.getMapper(UserMapper.class);
        User user = userMapper.findByid(1);
        System.out.println(user);
        
    }

}
