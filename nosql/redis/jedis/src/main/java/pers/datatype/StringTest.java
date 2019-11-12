package pers.datatype;

import pers.util.JedisUtil;
import redis.clients.jedis.Jedis;

/**
 * redis String 可以存 字符串，整数，浮点数
 */
public class StringTest {
    
    public static void main(String[] args) {
        
        Jedis jedis = JedisUtil.getJedis();
        jedis.del("int");
        jedis.set("int", "1");
        jedis.incrBy("int", 2);
        System.out.println(jedis.get("int"));
    
        jedis.close();
    }

}
