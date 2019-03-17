package com.mybatis.simple;


import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.mybatis.simple.bean.User;
import com.mybatis.simple.mapper.IUserMapper;

public class Main {
    
    public static void main(String[] args) throws Exception {
        
        // 读入配置文件
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        
        // 创建SessionFactoryBuilder
        SqlSessionFactoryBuilder sessionFactoryBuilder = new SqlSessionFactoryBuilder();
        
        // 创建SessionFactory
        SqlSessionFactory sessionFactory = sessionFactoryBuilder.build(is);
        
        // 创建Session
        SqlSession session = sessionFactory.openSession();

        // 获取Mapper代理实例
        IUserMapper userMapper = session.getMapper(IUserMapper.class);
        
        User user = userMapper.findById(1);
        
        System.out.println(user);
    }
    
}
