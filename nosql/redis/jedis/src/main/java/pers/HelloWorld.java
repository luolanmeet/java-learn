package pers;

import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * 最简单的使用
 */
public class HelloWorld {
    
    // 获取 redis.properties 配置文件
    static final ResourceBundle redisProperties = ResourceBundle.getBundle("redis");
    static final String HOST = redisProperties.getString("redis.host");
    static final int PORT = Integer.valueOf(redisProperties.getString("redis.port"));
    
    public static void main(String[] args) throws IOException {
    
        // 建立连接
        Jedis jedis = new Jedis(HOST, PORT);
        
        // 设值、获取值
        jedis.set("cck", "atman");
        System.out.println(jedis.get("cck"));
        
        jedis.close();
    }

}
