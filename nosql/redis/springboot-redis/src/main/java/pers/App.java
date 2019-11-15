package pers;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class App {
    
    public static void main(String[] args) {
    
        ConfigurableApplicationContext context = new SpringApplicationBuilder()
                .web(WebApplicationType.NONE)
                .sources(App.class)
                .run(args);
        
        RedisTemplate redisTemplate = (RedisTemplate) context.getBean("redisTemplate");
        
        redisTemplate.opsForValue().set("cck", "atman");
        System.out.println(redisTemplate.opsForValue().get("cck"));
    }

}
