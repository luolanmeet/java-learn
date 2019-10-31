package pers;

import org.springframework.aop.framework.ProxyFactory;
import pers.advice.MyAfterReturningAdvice;
import pers.advice.MyMethodBeforeAdvice;
import pers.advice.MyMethodInterceptor;
import pers.advice.MyThrowsAdvice;
import pers.advisor.MyStaticMethodMatcherPointcutAdvisor;

public class Main {
    
    public static void main(String[] args) {
        testAdvisor();
//        testAdvice();
    }
    
    private static void testAdvisor() {
    
        Cat cat = new Cat();
        MyMethodBeforeAdvice advice = new MyMethodBeforeAdvice();
    
        MyStaticMethodMatcherPointcutAdvisor pointcutAdvisor = new MyStaticMethodMatcherPointcutAdvisor();
        // 为切面类提供增强
        pointcutAdvisor.setAdvice(advice);
    
        // 创建ProxyFactory并设置代理目标、切面（ProxyFactory不再单独添加advice）
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(cat);
        proxyFactory.addAdvisor(pointcutAdvisor);
        
        // 生产代理实例
        Cat newCat = (Cat) proxyFactory.getProxy();
    
        newCat.sayHi();
        System.out.println();
        newCat.sayHello();
    }
    
    private static void testAdvice() {
    
        Cat cat = new Cat();
    
        // 创建ProxyFactory并设置代理目标和增强
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(cat);
        // 环绕增强的执行顺序和add的顺序有关，越先add越先执行
        proxyFactory.addAdvice(new MyMethodInterceptor());
        proxyFactory.addAdvice(new MyMethodBeforeAdvice());
        proxyFactory.addAdvice(new MyAfterReturningAdvice());
        proxyFactory.addAdvice(new MyThrowsAdvice());
    
        // 生产代理实例
        Cat newCat = (Cat) proxyFactory.getProxy();
    
        newCat.sayHi();
        newCat.sayHello();
    }
    
}
