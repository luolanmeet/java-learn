package pers.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

public class JedisUtil {
    
    // 获取 redis.properties 配置文件
    private static final ResourceBundle REDIS_PROPERTIES = ResourceBundle.getBundle("redis");
    private static final String HOST = REDIS_PROPERTIES.getString("redis.host");
    private static final int PORT = Integer.valueOf(REDIS_PROPERTIES.getString("redis.port"));
    
    private static JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    private static JedisPool jedisPool = new JedisPool(jedisPoolConfig, HOST, PORT);
    
    public static Jedis getJedis() {
        return jedisPool.getResource();
    }
    
    public static void close() {
        jedisPool.close();
    }

}
