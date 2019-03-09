package pers.mybatis.mapping;

public interface SqlSource {
    
    BoundSql getBoundSql(Object parameterObject);
    
}
