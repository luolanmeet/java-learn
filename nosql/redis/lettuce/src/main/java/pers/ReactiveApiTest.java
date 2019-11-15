package pers;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.reactive.RedisReactiveCommands;

import java.util.concurrent.TimeUnit;

/**
 * 支持响应式编程
 */
public class ReactiveApiTest {
    
    public static void main(String[] args) {
    
        // 创建客户端
        RedisClient redisClient = RedisClient.create("redis://117.48.228.179:6379");
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        RedisReactiveCommands<String, String> reactive = connect.reactive();
    
        reactive.set("cck", "atman");
        reactive.get("cck").subscribe(System.out::println);
    
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    
        connect.close();
        redisClient.shutdown();
    }

}
