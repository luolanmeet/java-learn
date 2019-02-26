package pers.mybatis.binding;

import pers.mybatis.annotations.Select;
import pers.mybatis.session.SqlSession;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MapperProxy implements InvocationHandler {

    private final SqlSession sqlSession;

    public MapperProxy(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Annotation[] annotations = method.getAnnotations();
        if (annotations != null && annotations.length > 0) {

            Select select = (Select) annotations[0];
            String sql = select.value();
            return sqlSession.selectOne(sql, String.valueOf(args[0]));
        }

        return method.invoke(this, args);
    }
}
