package pers.mybatis.session;

import pers.mybatis.session.defaults.DefaultSqlSessionFactory;

import java.util.Map;

public class SqlSessionFactoryBuilder {

    // 传入配置文件xml
    // 解析配置文件
    // 创建SqlSessionFactory

    public SqlSessionFactory build(Configuration configuration) {
        return new DefaultSqlSessionFactory(configuration);
    }

}
