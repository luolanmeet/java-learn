package pers.mybatis.session;

public interface SqlSession {

    <T> T getMapper(Class<T> type);

    <T> T selectOne(String statement, Object parameter);

    Configuration getConfiguration();

}
