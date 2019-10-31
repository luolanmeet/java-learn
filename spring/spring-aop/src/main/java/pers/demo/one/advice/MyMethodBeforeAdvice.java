package pers.demo.one.advice;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * 前置增强
 */
public class MyMethodBeforeAdvice implements MethodBeforeAdvice {
    
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("MethodBeforeAdvice#before : " + method.getName());
    }
    
}
