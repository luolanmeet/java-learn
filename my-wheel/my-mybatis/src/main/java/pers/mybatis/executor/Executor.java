package pers.mybatis.executor;

import pers.mybatis.mapping.MappedStatement;

public interface Executor {

    <T> T query(
            MappedStatement ms,
            Object parameter);

}
