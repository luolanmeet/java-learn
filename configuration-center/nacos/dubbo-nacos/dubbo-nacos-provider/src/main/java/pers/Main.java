package pers;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws InterruptedException {

        new SpringApplicationBuilder().sources(Main.class)
                .web(WebApplicationType.NONE)
                .run(args);

        new CountDownLatch(1).await();
    }

}
