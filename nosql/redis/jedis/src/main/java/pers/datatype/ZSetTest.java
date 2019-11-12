package pers.datatype;

import pers.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * 带分数的有序集合
 */
public class ZSetTest {
    
    public static void main(String[] args) {
    
        Jedis jedis = JedisUtil.getJedis();
    
        jedis.zadd("myzset", 90, "go");
        jedis.zadd("myzset", 20, "java");
        jedis.zadd("myzset", 30, "python");
        jedis.zadd("myzset", 90, "ruby");
        jedis.zadd("myzset", 40, "erlang");
        jedis.zadd("myzset", 70, "cpp");
        
        Set<String> set = jedis.zrangeByScore("myzset", 50, 100);
        // 升序排列
        System.out.println(set);
    }
    
}
