package pers.mybatis.session;

public interface SqlSession {

    <T> T getMapper(Class<T> type);

    <T> T selectOne(String statement, String parameter);

}
