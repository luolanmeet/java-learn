package pers.mybatis.builder;

import java.util.List;

import pers.mybatis.mapping.BoundSql;
import pers.mybatis.mapping.ParameterMapping;
import pers.mybatis.mapping.SqlSource;
import pers.mybatis.session.Configuration;

public class StaticSqlSource implements SqlSource {

    private String sql;
    private List<ParameterMapping> parameterMappings;
    private Configuration configuration;

    public StaticSqlSource(
            Configuration configuration, 
            String sql, 
            List<ParameterMapping> parameterMappings) {
        
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.configuration = configuration;
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return new BoundSql(configuration, sql, parameterMappings, parameterObject);
    }

}
