package pers;

import org.springframework.aop.framework.ProxyFactory;
import pers.advice.MyAfterReturningAdvice;
import pers.advice.MyMethodBeforeAdvice;
import pers.advice.MyMethodInterceptor;
import pers.advice.MyThrowsAdvice;

public class Main {
    
    public static void main(String[] args) {
    
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
    }

}
