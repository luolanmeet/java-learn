package pers.mybatis.executor;

public interface Executor {

    <T> T query(
            String statement,
            String parameter);

}
