package pers.custom.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import pers.custom.annotation.config.Config;

public class Main {

    public static void main(String[] args) {
        
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        HandlerDispatcherServlet servlet = context.getBean(HandlerDispatcherServlet.class);
        
        servlet.handle(Cmd.REGISTER);
        servlet.handle(Cmd.LOGIN);
        servlet.handle(Cmd.LOGOUT);

        context.close();
    }

}
