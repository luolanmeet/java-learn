package per.javassist;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

/**
 * 通过ProxyFactory进行代理
 * @author cck
 */
public class MainTwo {
    
    public static void main(String[] args) throws Exception {
        
        // 创建代理工厂
        ProxyFactory proxyFactory = new ProxyFactory();
        
        // 设置被代理类的类型
        proxyFactory.setSuperclass(RealSubject.class);
        
        // 创建代理类的class
        Class<?> clazz = proxyFactory.createClass();
        
        // 创建实例
        Object obj = clazz.newInstance();
        
        ((ProxyObject) obj).setHandler(new MethodHandler() {

            RealSubject real = new RealSubject();
            
            @Override
            public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
                
                System.out.println("before method invoke");
                Object res = thisMethod.invoke(real, args);
                System.out.println("after method invoke");
                
                return res;
            }
            
        });
        
        RealSubject rsProxy = (RealSubject) obj;
        rsProxy.sayHello("cck");
    }
    
}
