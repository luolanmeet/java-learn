package pers.pool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

/**
 * Jedis连接池，解决Jedis实例线程不安全问题
 */
public class JedisPoolTest {
    
    // 获取 redis.properties 配置文件
    static final ResourceBundle REDIS_PROPERTIES = ResourceBundle.getBundle("redis");
    static final String HOST = REDIS_PROPERTIES.getString("redis.host");
    static final int PORT = Integer.valueOf(REDIS_PROPERTIES.getString("redis.port"));
    
    public static void main(String[] args) {
    
        // 创建 JedisPool 对象
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, HOST, PORT);
    
        // 获取
        Jedis jedis = jedisPool.getResource();
    
        jedis.set("cck", "atman");
        System.out.println(jedis.get("cck"));
    
        jedisPool.close();
    }

}
