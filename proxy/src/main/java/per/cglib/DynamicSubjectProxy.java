package per.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class DynamicSubjectProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("before method invoke");
        Object result = proxy.invokeSuper(obj, args);
        System.out.println("after method invoke");
        return result;
    }

}
