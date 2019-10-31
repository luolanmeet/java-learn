package pers.advice;

import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * 异常增强
 */
public class MyThrowsAdvice implements ThrowsAdvice {
    
    /**
     * 看ThrowsAdvice，有说要实现哪些方法
     */
    public void afterThrowing(Method method, Object[] args, Object target, Exception ex) {
        System.out.println("ThrowsAdvice#afterThrowing : " + method.getName() + " exception");
    }

}
