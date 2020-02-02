package pers;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class App {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = new SpringApplicationBuilder()
                .web(WebApplicationType.NONE)
                .sources(App.class)
                .run(args);

        RedissonClient client = context.getBean(RedissonClient.class);

        RBucket<String> bucket = client.getBucket("cck");
        // 设值时，服务器的值也改变
        bucket.set("atman");
        System.out.println(bucket.get());

        bucket.set("atman222");
        bucket = client.getBucket("cck");
        System.out.println(bucket.get());

        client.shutdown();
    }

}
