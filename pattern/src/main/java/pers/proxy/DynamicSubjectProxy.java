package pers.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicSubjectProxy implements InvocationHandler {

    private Object object;

    public Object bind(Object object) {
        this.object = object;
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), object.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before method invoke");
        Object result = method.invoke(object, args);
        System.out.println("after method invoke");
        return result;
    }
}
