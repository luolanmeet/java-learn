package pers.demo.one.advisor;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

import java.lang.reflect.Method;

/**
 * 静态普通方法名匹配切面
 */
public class MyStaticMethodMatcherPointcutAdvisor extends StaticMethodMatcherPointcutAdvisor {
    
    private static String METHOD_NAME = "sayHi";
    
    /**
     * 静态方法名匹配判断
     */
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return METHOD_NAME.equals(method.getName());
    }
    
    /**
     * 覆盖getClassFilter，可以定义匹配那些类
     */
    public ClassFilter getClassFilter() {
        
        return new ClassFilter() {
            @Override
            public boolean matches(Class<?> clazz) {
                return true;
            }
        };
    }
    
}
