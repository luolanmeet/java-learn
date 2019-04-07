package per.demo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackages={"per.demo2.health"})
public class Application {
    
    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

    }
}
