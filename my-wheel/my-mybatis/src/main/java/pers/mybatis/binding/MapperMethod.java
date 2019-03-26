package pers.mybatis.binding;

import java.lang.reflect.Method;
import java.util.HashMap;

import pers.mybatis.mapping.MappedStatement;
import pers.mybatis.mapping.SqlCommandType;
import pers.mybatis.reflection.ParamNameResolver;
import pers.mybatis.session.Configuration;
import pers.mybatis.session.SqlSession;

public class MapperMethod {

    // 方法对应的SQL信息，真正的SQL在MappedStatement
    private final SqlCommand command;
    // 方法的签名（参数、返回值等）
    private final MethodSignature method;

    public MapperMethod(Class<?> mapperInterface, Method method, Configuration config) {
        this.command = new SqlCommand(config, mapperInterface, method);
        this.method = new MethodSignature(config, mapperInterface, method);
    }

    public Object execute (SqlSession sqlSession, Object[] args) {

        Object result = null;
        switch (command.getType()) {
        
            case SELECT:
                Object param = method.convertArgsToSqlCommandParam(args);
                result = sqlSession.selectOne(command.getName(), param);
                break;
                
            default:
                break;
        }
        return result;
    }

    private static class MethodSignature {
        
        private final ParamNameResolver paramNameResolver;
        
        public MethodSignature(Configuration config, Class<?> mapperInterface, Method method) {
            
            this.paramNameResolver = new ParamNameResolver(config, method);
        }
        
        // 封装方法的入参
        public Object convertArgsToSqlCommandParam(Object[] args) {
            return paramNameResolver.getNamedParams(args);
        }
        
    }

    public static class SqlCommand {

        // 组成：类路径.方法名
        private final String name;
        private final SqlCommandType type;

        public SqlCommand(Configuration configuration, Class<?> mapperInterface, Method method) {

            final String methodName = method.getName();
            final Class<?> declaringClass = method.getDeclaringClass();
            // 根据 接口路径+方法名 在configuration中找到MappedStatement
            MappedStatement ms = resolveMappedStatement(
                    mapperInterface,
                    methodName,
                    declaringClass,
                    configuration);

            this.name = ms.getId();
            this.type = ms.getSqlCommandType();
        }

        // 获取 MappedStatement
        private MappedStatement resolveMappedStatement(Class<?> mapperInterface, String methodName,
                                                       Class<?> declaringClass, Configuration configuration) {

            String statementId = mapperInterface.getName() + "." + methodName;
            return configuration.getMappedStatement(statementId);
        }

        public String getName() {
            return name;
        }

        public SqlCommandType getType() {
            return type;
        }
    }
    
    public static class ParamMap<V> extends HashMap<String, V> {

        private static final long serialVersionUID = -2212268410512043556L;

        @Override
        public V get(Object key) {

            if (!super.containsKey(key)) {
                throw new RuntimeException("Parameter '" + key + "' not found. Available parameters are " + keySet());
            }

            return super.get(key);
        }

    }
    
}
