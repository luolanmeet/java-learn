package pers.pool;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 客户端的实现的数据分片，Jedis提供实现
 * 使用了一致性哈希，利用红黑树实现
 */
public class ShardedJedisPoolTest {
    
    static final ResourceBundle REDIS_PROPERTIES = ResourceBundle.getBundle("redis");
    static final String HOST = REDIS_PROPERTIES.getString("redis.host");
    static final int PORT = Integer.valueOf(REDIS_PROPERTIES.getString("redis.port"));
    
    public static void main(String[] args) {
    
        JedisPoolConfig poolConfig = new JedisPoolConfig();
    
        // Redis服务器，有多个机器可以添加多个
        JedisShardInfo shardInfo = new JedisShardInfo(HOST, PORT);
    
        // 连接池
        List<JedisShardInfo> jedisShardInfos = Arrays.asList(shardInfo);
        ShardedJedisPool jedisPool = new ShardedJedisPool(poolConfig, jedisShardInfos);
    
        // 使用
        ShardedJedis jedis = jedisPool.getResource();
        jedis.del("cck");
        jedis.set("cck", "atman");
        System.out.println(jedis.get("cck"));
    
        jedisPool.close();
    }

}
