package pers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;

/**
 * @auther ken.ck
 * @date 2021/6/19 22:47
 */
@SpringBootApplication
public class Application {

    private static CountDownLatch CDL = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
        CDL.await();
    }

}
