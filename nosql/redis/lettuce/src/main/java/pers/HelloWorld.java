package pers;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

/**
 * lettuce 支持异步请求、且客户端线程安全
 */
public class HelloWorld {
    
    public static void main(String[] args) {
    
        // 创建客户端
        RedisClient redisClient = RedisClient.create("redis://117.48.228.179:6379");
        StatefulRedisConnection<String, String> connect = redisClient.connect();
    
        // 创建命令
        RedisCommands<String, String> commands = connect.sync();
        commands.set("cck", "atman");
        System.out.println(commands.get("cck"));
        
        connect.close();
        redisClient.shutdown();
    }

}
