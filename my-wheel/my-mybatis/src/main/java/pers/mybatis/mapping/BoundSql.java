package pers.mybatis.mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import pers.mybatis.session.Configuration;

/**
 * 真正存放SQL的地方
 * @author cck
 */
@Data
public class BoundSql {

    // 我们的SQL语句
    private String sql;
    private List<ParameterMapping> parameterMappings;
    private Object parameterObject;
    private Map<String, Object> additionalParameters;
//    private MetaObject metaParameters;

    public BoundSql(
            Configuration configuration, 
            String sql, 
            List<ParameterMapping> parameterMappings,
            Object parameterObject) {
        
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.parameterObject = parameterObject;
        this.additionalParameters = new HashMap<String, Object>();
//        this.metaParameters = configuration.newMetaObject(additionalParameters);
    }
    
}
