package com.mybatis.simple;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.mybatis.simple.bean.User;
import com.mybatis.simple.mapper.IUserMapper;

public class Main {
    
    public static void main(String[] args) throws Exception {

        SqlSession session = getSqlSession();

        testMapper(session);
        testPageHelper(session);

        session.commit();
        session.close();
    }

    private static void testMapper(SqlSession session) {

        // 获取Mapper代理实例
        IUserMapper userMapper = session.getMapper(IUserMapper.class);

//        User user = userMapper.findById(1);
        User user = userMapper.findByIdAndName(1, "cck");
        System.out.println(user);

        user.setName("cck");
        user.setAddress("中国");
        userMapper.save(user);
    }

    private static void testPageHelper(SqlSession session) throws IOException {

        IUserMapper userMapper = session.getMapper(IUserMapper.class);

        PageHelper.startPage(0, 2);
        List<User> users = userMapper.findByName("c");
        PageInfo page = new PageInfo(users, 2);
        System.out.println(users);
        System.out.println(page);
    }

    private static SqlSession getSqlSession() throws IOException {

        // 读入配置文件
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");

        // 创建SessionFactoryBuilder
        SqlSessionFactoryBuilder sessionFactoryBuilder = new SqlSessionFactoryBuilder();

        // 创建SessionFactory
        SqlSessionFactory sessionFactory = sessionFactoryBuilder.build(is);

        // 创建Session
        return sessionFactory.openSession();
    }

}
