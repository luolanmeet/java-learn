package pers.mybatis.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pers.mybatis.mapping.ParameterMapping;
import pers.mybatis.mapping.SqlSource;
import pers.mybatis.parsing.GenericTokenParser;
import pers.mybatis.parsing.TokenHandler;
import pers.mybatis.session.Configuration;

public class SqlSourceBuilder extends BaseBuilder {

    public SqlSourceBuilder(Configuration configuration) {
        super(configuration);
    }
    
    public SqlSource parse(
            String originalSql, Class<?> parameterType, 
            Map<String, Object> additionalParameters) {
        
        ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler(
                configuration, parameterType,
                additionalParameters);
        GenericTokenParser parser = new GenericTokenParser("#{", "}", handler);
        String sql = parser.parse(originalSql);
        return new StaticSqlSource(configuration, sql, handler.getParameterMappings());
    }
    
    private static class ParameterMappingTokenHandler extends BaseBuilder implements TokenHandler {
        
        private List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();
        private Class<?> parameterType;
//        private MetaObject metaParameters;

        public ParameterMappingTokenHandler(Configuration configuration, Class<?> parameterType, Map<String, Object> additionalParameters) {
          super(configuration);
          this.parameterType = parameterType;
//          this.metaParameters = configuration.newMetaObject(additionalParameters);
        }

        @Override
        public String handleToken(String content) {
//            parameterMappings.add(buildParameterMapping(content));
            return "?";
        }
        
        public List<ParameterMapping> getParameterMappings() {
            return parameterMappings;
        }
        
    }
    
}
