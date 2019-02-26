package pers.mybatis.session.defaults;

import pers.mybatis.executor.Executor;
import pers.mybatis.session.Configuration;
import pers.mybatis.session.SqlSession;
import pers.mybatis.session.SqlSessionFactory;

import java.util.Map;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    // 构建一个SqlSession
    // 主要是放入配置文件、Executor

    @Override
    public SqlSession openSession() {
        Executor executor = configuration.newExecutor();
        return new DefaultSqlSession(configuration, executor);
    }
}
