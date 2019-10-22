package pers;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import pers.service.ProgrammerA;
import pers.service.ProgrammerB;

@ComponentScan("pers")
@EnableAspectJAutoProxy
//@EnableAspectJAutoProxy(proxyTargetClass = true) 不一样的配置环境或框架版本下，可能需要设置proxyTargetClass属性
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
