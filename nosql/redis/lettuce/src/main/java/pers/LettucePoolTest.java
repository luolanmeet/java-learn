package pers;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * 使用连接池
 */
public class LettucePoolTest {
    
    public static void main(String[] args) throws Exception {
    
        // 创建客户端
        RedisClient redisClient = RedisClient.create("redis://117.48.228.179:6379");
    
        // 线程池
        GenericObjectPool<StatefulRedisConnection<String, String>> pool = ConnectionPoolSupport.createGenericObjectPool(
                () -> redisClient.connect(),
                new GenericObjectPoolConfig());
        
        try (StatefulRedisConnection<String, String> connect = pool.borrowObject()) {
    
            RedisCommands<String, String> commands = connect.sync();
            commands.set("cck", "atman");
            System.out.println(commands.get("cck"));
        }
        
        pool.close();
        redisClient.shutdown();
    }

}
