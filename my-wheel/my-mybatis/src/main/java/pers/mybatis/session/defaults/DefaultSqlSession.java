package pers.mybatis.session.defaults;

import pers.mybatis.executor.Executor;
import pers.mybatis.mapping.MappedStatement;
import pers.mybatis.session.Configuration;
import pers.mybatis.session.SqlSession;

public class DefaultSqlSession implements SqlSession {

    private final Configuration configuration;

    private final Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    @Override
    public <T> T getMapper(Class<T> clazz) {
        return configuration.getMapper(clazz, this);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {

        MappedStatement ms = configuration.getMappedStatement(statement);
        return executor.query(ms, parameter);
    }

    @Override
    public Configuration getConfiguration() {
        return this.configuration;
    }
}
