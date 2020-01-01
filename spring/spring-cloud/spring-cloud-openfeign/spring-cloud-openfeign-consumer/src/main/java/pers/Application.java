package pers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@EnableFeignClients
@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(Application.class);
        ConfigurableApplicationContext applicationContext = application.run(args);

        IHelloService helloService = applicationContext.getBean(IHelloService.class);
        System.out.println(helloService.hello("cck"));

        IHelloWorldService helloWorldService = applicationContext.getBean(IHelloWorldService.class);
        System.out.println(helloWorldService.helloWorld());
    }

}
