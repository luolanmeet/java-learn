package pers.demo.one.advice;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * 后置增强
 */
public class MyAfterReturningAdvice implements AfterReturningAdvice {
    
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("AfterReturningAdvice#afterReturning : " + method.getName());
    }
    
}
