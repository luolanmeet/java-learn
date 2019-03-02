package pers.mybatis.binding;

import pers.mybatis.annotations.Select;
import pers.mybatis.session.SqlSession;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MapperProxy implements InvocationHandler {

    private final SqlSession sqlSession;

    public MapperProxy(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 如果是Object中的方法（toString这些），则直接执行
        if (Object.class.equals(method.getDeclaredAnnotations())) {
            return method.invoke(args);
        }
        // 如果method方法的权限修饰符是public并且由接口提供（默认方法），则抛异常（mybatis不是这样处理）
        if (isDefaultMethod(method)) {
            throw new RuntimeException("my-mybatis 不支持接口有默认方法");
        }

        Annotation[] annotations = method.getAnnotations();
        if (annotations != null && annotations.length > 0) {

            Select select = (Select) annotations[0];
            String sql = select.value();
            return sqlSession.selectOne(sql, String.valueOf(args[0]));
        }

        return method.invoke(this, args);
    }

    private boolean isDefaultMethod(Method method) {
        return
                ((method.getModifiers() & (Modifier.ABSTRACT | Modifier.PUBLIC | Modifier.STATIC))
                        == Modifier.PUBLIC)
                        &&
                        method.getDeclaringClass().isInterface();
    }

}
