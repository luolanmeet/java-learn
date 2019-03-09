package pers.mybatis.binding;

import pers.mybatis.mapping.MappedStatement;
import pers.mybatis.mapping.SqlCommandType;
import pers.mybatis.session.Configuration;
import pers.mybatis.session.SqlSession;

import java.lang.reflect.Method;

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
                result = sqlSession.selectOne(command.getName(), args[0]);
                break;
        }
        return result;
    }

    private static class MethodSignature {

        public MethodSignature(Configuration config, Class<?> mapperInterface, Method method) {
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

}
