package pers.demo.three;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import pers.demo.three.service.ProgrammerA;
import pers.demo.three.service.ProgrammerB;

@ComponentScan("pers.demo")
@EnableAspectJAutoProxy
// 不一样的配置环境或框架版本下，可能需要设置proxyTargetClass属性，强制开启CGLIB动态代理
// @EnableAspectJAutoProxy(proxyTargetClass = true)
public class Main {
    
    public static void main(String[] args) {
        
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        ProgrammerA pa = context.getBean(ProgrammerA.class);
        ProgrammerB pb = context.getBean(ProgrammerB.class);
        
        pa.writeCode("Java");
        pa.learn();
        
        pb.writeCode("Go");
        pb.learn();
        
        context.close();
    }
    
}
