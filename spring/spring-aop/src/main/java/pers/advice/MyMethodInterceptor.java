package pers.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * 环绕增强
 */
public class MyMethodInterceptor implements MethodInterceptor {
    
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
    
        System.out.println("MethodInterceptor#invoke1");
    
        Object result = invocation.proceed();
    
        System.out.println("MethodInterceptor#invoke2");
        
        return result;
    }
}
