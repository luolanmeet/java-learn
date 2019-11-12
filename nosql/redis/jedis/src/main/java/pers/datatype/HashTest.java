package pers.datatype;

import pers.util.JedisUtil;
import redis.clients.jedis.Jedis;

/**
 *
 */
public class HashTest {
    
    public static void main(String[] args) {
    
        Jedis jedis = JedisUtil.getJedis();
        
        jedis.hset("rectangle", "height", "300");
        jedis.hset("rectangle", "wigth", "400");
    
        System.out.println(jedis.hget("rectangle", "height"));
        System.out.println(jedis.hmget("rectangle", "height", "wigth"));
        System.out.println(jedis.hgetAll("rectangle"));
    
        jedis.close();
    }

}
