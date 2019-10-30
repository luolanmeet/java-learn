package pers.bean.replaced;

import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;

public class Wine implements MethodReplacer {
    
    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
        System.out.println("宾克斯的美酒");
        return obj;
    }
    
}
