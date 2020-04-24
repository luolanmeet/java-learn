package pers.proxy;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;

public class Main {

    public static void main(String[] args) {

        Subject subject = new RealSubject();
        StaticSubjectProxy staticSubjectProxy = new StaticSubjectProxy(subject);
        staticSubjectProxy.sayHello("cck");

        System.out.println();

        DynamicSubjectProxy dynamicSubjectProxy = new DynamicSubjectProxy();
        Subject sb = (Subject) dynamicSubjectProxy.bind(subject);
        sb.sayHello("cck");

        // 保存动态代理生产类的类文件
        // 动态代理类类名
        String proxyClassName = sb.getClass().getName();
        // 字节码数组，之后用jad反编译去看或是 javap -v -c
        byte[] bytes = ProxyGenerator.generateProxyClass(proxyClassName, new Class[]{Subject.class});

        try (FileOutputStream fos = new FileOutputStream(proxyClassName + ".class")) {
            fos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
