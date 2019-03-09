package pers.mybatis.binding;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

import pers.mybatis.session.SqlSession;

/**
 * 主要职责就是获取 根据method 获取 MapperMethod
 * 之后就交给MapperMethod了
 * @param <T>
 */
public class MapperProxy<T> implements InvocationHandler {

    private final SqlSession sqlSession;
    private final Class<T> mapperInterface;
    private final Map<Method, MapperMethod> methodCache;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface, Map<Method, MapperMethod> methodCache) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
        this.methodCache = methodCache;
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

        // 获取MapperMethod，调用execute方法
        final MapperMethod mapperMethod = cachedMapperMethod(method);

        return mapperMethod.execute(sqlSession, args);
    }

    private MapperMethod cachedMapperMethod(Method method) {

        MapperMethod mapperMethod = methodCache.get(method);

        if (mapperMethod == null) {
            mapperMethod = new MapperMethod(mapperInterface, method, sqlSession.getConfiguration());
            methodCache.put(method, mapperMethod);
        }
        return mapperMethod;
    }
    
    private boolean isDefaultMethod(Method method) {
        return
                ((method.getModifiers() & (Modifier.ABSTRACT | Modifier.PUBLIC | Modifier.STATIC))
                        == Modifier.PUBLIC)
                        &&
                        method.getDeclaringClass().isInterface();
    }

}
