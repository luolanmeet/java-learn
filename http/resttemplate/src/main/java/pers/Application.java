package pers;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;
import pers.service.DemoService;

@EnableRetry(proxyTargetClass = true)
@ComponentScan("pers")
public class Application {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        DemoService service = context.getBean(DemoService.class);

//        service.getForEntity();
        service.errorMethod();
    }

}
