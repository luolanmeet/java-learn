package per.cglib;

import net.sf.cglib.proxy.Enhancer;

public class Main {

    public static void main(String[] args) {

        RealSubject realSubject = new RealSubject();
        DynamicSubjectProxy dynamicSubjectProxy = new DynamicSubjectProxy();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(RealSubject.class);
        enhancer.setCallback(dynamicSubjectProxy);
        RealSubject sr = (RealSubject) enhancer.create();

        sr.sayHello("cck");
    }

}
