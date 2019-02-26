package pers.mybatis.session;

import pers.mybatis.binding.MapperProxy;
import pers.mybatis.executor.Executor;
import pers.mybatis.executor.SimpleExecutor;

import java.lang.reflect.Proxy;

public class Configuration {

    public Executor newExecutor() {
        return new SimpleExecutor();
    }

    @SuppressWarnings("unchecked")
    public <T> T getMapper(
            Class<T> clazz,
            SqlSession sqlSession) {

        // 动态代理生成对象
        return (T) Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                new Class[] {clazz},
                new MapperProxy(sqlSession));
    }

}
