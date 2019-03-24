package pers.mybatis.test;

import pers.mybatis.session.Configuration;
import pers.mybatis.session.SqlSession;
import pers.mybatis.session.SqlSessionFactory;
import pers.mybatis.session.SqlSessionFactoryBuilder;

public class Main {

    public static void main(String[] args) {

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(new Configuration());
        SqlSession session = sqlSessionFactory.openSession();

        TestMapper testMapper = session.getMapper(TestMapper.class);
        Test test = testMapper.findByid(1);
        System.out.println(test);
        
        test = testMapper.findByIdAndName(1, "233");
        System.out.println(test);
        
        test = testMapper.findByIdAndNums(2, 111);
        System.out.println(test);
    }

}
