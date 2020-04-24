package per.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

public class Main {

    public static void main(String[] args) {

        // 保存cglib生产的类的文件
        // cglib生产类并加载到内存后，会把类文件删除
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "E://");

        DynamicSubjectProxy dynamicSubjectProxy = new DynamicSubjectProxy();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(RealSubject.class);
        enhancer.setCallback(dynamicSubjectProxy);
        RealSubject sr = (RealSubject) enhancer.create();

        sr.sayHello("cck");
        System.out.println();
        sr.sayHi("ck");
    }

}
