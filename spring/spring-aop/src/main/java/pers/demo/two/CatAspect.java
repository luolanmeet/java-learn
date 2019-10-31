package pers.demo.two;

import org.aspectj.lang.ProceedingJoinPoint;

public class CatAspect {
    
    /**
     * 前置增强
     */
    public void beforeAdvice(String name) {
        System.out.println("前置增强，name：" + name);
    }
    
    /**
     * 后置异常增强
     */
    public void afterExceptionAdvice(String name) {
        System.out.println("后置异常增强，name：" + name);
    }
    
    /**
     * 后置返回增强
     */
    public void afterReturningAdvice(String name) {
        System.out.println("后置返回增强，name：" + name);
    }
    
    /**
     * 后置最终增强
     */
    public void afterAdvice(String name) {
        System.out.println("后置最终增强，name：" + name);
    }
    
    /**
     * 环绕增强
     */
    public Object roundAdvice(ProceedingJoinPoint p, String name) {
    
        System.out.println("环绕增强开始，name：" + name);
        Object resutl = null;
        try {
            resutl = p.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("环绕增强结束，name：" + name);
        return resutl;
    }
    
}
