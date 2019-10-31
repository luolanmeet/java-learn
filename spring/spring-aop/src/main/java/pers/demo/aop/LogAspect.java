package pers.demo.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    
    @Pointcut("@target(pers.demo.annotation.WorkLog)")
    public void log() {}
    
    final String BEFORE_TEMPLATE 
        = "call class [%s]'s method[%s] with args[%s]";
    final String AFTER_RETURN_TEMPLATE 
        = "call class [%s]'s method[%s] with args[%s] --> result[%s].";
    
    @Before("log()")
    public void callBefore(JoinPoint point) {
        
        // 获取类名
        String className = point.getSignature().getDeclaringTypeName();
        // 获取调用的方法名
        String methodName = point.getSignature().getName();
        // 获取参数
        Object[] args = point.getArgs();
        String argsStr = Arrays.toString(args);
        
        System.out.println(String.format(BEFORE_TEMPLATE, className, methodName, argsStr));
    }
    
    @AfterReturning(pointcut="log()", returning="_return")
    public void callAfter(JoinPoint point, Object _return) {
        
        String className = point.getSignature().getDeclaringTypeName();
        String methodName = point.getSignature().getName();
        Object[] args = point.getArgs();
        String argsStr = Arrays.toString(args);
        
        String returnStr = _return == null ? "" : _return.toString();
        
        System.out.println(String.format(AFTER_RETURN_TEMPLATE, className, methodName, argsStr, returnStr));
    }
    
}
