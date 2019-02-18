package pers.custom.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("pers.custom.annotation")
public class Main {

    public static void main(String[] args) {
        
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        HandlerDispatcherServlet servlet = context.getBean(HandlerDispatcherServlet.class);
        
        servlet.handle(Cmd.REGISTER);
        servlet.handle(Cmd.LOGIN);
        servlet.handle(Cmd.LOGOUT);

        context.close();
    }

}
