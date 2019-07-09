package per.javassist;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtNewMethod;

/**
 * 直接创建一个类
 * @author cck
 */
public class MainOne {

    /**
     * 各个类的用处见 use.demo 
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        
        String className = RealSubject.class.getName() + "JavassistGenerateClass";
        
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.makeClass(className);

        // 设置父类
        CtClass parentCtClass = classPool.get(RealSubject.class.getName());
        ctClass.setSuperclass(parentCtClass);
        
        // 创建被代理类实例，这里在代码里边 new 了一个
        ctClass.addField(CtField.make(
                "public " + RealSubject.class.getName() + " real = new " +
                RealSubject.class.getName() + "();", ctClass));
        
        // 设置被动态代理方法
        ctClass.addMethod(CtNewMethod.make(""
                + "public void sayHello(String name) {"
                    + "System.out.println(\"before\");"
                    + "real.sayHello(name);"
                    + "System.out.println(\"after\");"
                + "}",
                ctClass));
        
        // 创建实例
        Class<?> clazz = ctClass.toClass();
        Constructor<?> constructor = clazz.getConstructor();   
        Object obj = constructor.newInstance();
        
        Method method = clazz.getMethod("sayHello", String.class);
        method.invoke(obj, "cck");
        
        RealSubject rsProxy = (RealSubject) obj;
        rsProxy.sayHello("cck");
    }
    
}
