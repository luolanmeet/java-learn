package pers.mybatis.mapping;

import lombok.Data;

/**
 * 存放SQL语句
 */
@Data
public final class MappedStatement {

    // id的组成： 接口，名全路径.方法名
    private String id;
    // SQL类型
    private SqlCommandType sqlCommandType;
    // 存放SQL
    private SqlSource sqlSource;
    
    public BoundSql getBoundSql(Object parameterObject) {
        
        // 这里获取我们的SQL
        return sqlSource.getBoundSql(parameterObject);
        
    }
    
}
