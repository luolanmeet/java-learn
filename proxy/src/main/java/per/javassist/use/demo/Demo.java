package per.javassist.use.demo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.junit.Test;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;

/**
 * javassist使用
 * @author cck
 */
public class Demo {
    
    /**
     * 使用 javassist 直接生成一个类 
     * @throws Exception 
     */
    @Test
    public void dynGenerateClass() throws Exception {
        
        // 缓存了创建的class，一个Hashtable
        ClassPool classPool = ClassPool.getDefault();
        
        // 创建一个类名为JavassistGenerateClass的类
        CtClass ctClass = classPool.makeClass("per.javassist.use.demo.JavassistGenerateClass");
        
        // 创建一个属性
        CtField ctField = CtField.make("private int val = 3;", ctClass);
        // 添加属性
        ctClass.addField(ctField);
        
        // 创建一个带参的构造方法
        CtConstructor ctConstructor = CtNewConstructor.make("public JavassistGenerateClass(int val) {this.val = val;}", ctClass);
        // 创建了一个默认的构造方法
        CtConstructor defaultConstructor = CtNewConstructor.defaultConstructor(ctClass);
        // 添加构造方法
        ctClass.addConstructor(ctConstructor);
        ctClass.addConstructor(defaultConstructor);

        // 创建一个方法
        CtMethod ctMethod = CtNewMethod.make("public void showVal() {System.out.println(val);}", ctClass);
        ctMethod.insertBefore("System.out.println(\"before\");");
        ctMethod.insertAfter("System.out.println(\"after\");");
        // 添加方法
        ctClass.addMethod(ctMethod);
        
        // 如果是继承了父类，没法用getDeclaredMethod获取父类的方法。（代理注释里写了）
        // 使用getMethods是可以的，不过获得的父类的CtMethod实例没办法是用 insertBefore 这类操作
        System.out.println(ctClass.getDeclaredMethod("showVal"));
        
        // 创建实例
        Class<?> clazz = ctClass.toClass();
        Constructor<?> constructor = clazz.getConstructor(int.class);   
        Method method = clazz.getMethod("showVal");
        Object obj = constructor.newInstance(10086);
        method.invoke(obj);
    }
    
}
