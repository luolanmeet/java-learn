package com.mybatis.simple.plugins;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;

import java.util.Properties;

/**
 * mybatis 插件，用于拦截mybatis方法
 * 用到责任链模式 + 动态代理 + 反射机制
 * https://mybatis.org/mybatis-3/zh/configuration.html#plugins
 */
@Intercepts({
        @Signature(
                type = Executor.class,
                method = "update",
                args = {MappedStatement.class, Object.class}
        )
})
public class MyPlugin implements Interceptor {

    private Properties properties;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object result = invocation.proceed();
        System.out.println("MyPlugin#intercept : " + result);
        return result;
    }

    @Override
    public Object plugin(Object target) {
        // 为这个target创建一个动态代理对象
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
        System.out.println(properties);
    }
}
