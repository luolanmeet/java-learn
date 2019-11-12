package pers.pubsub;

import pers.util.JedisUtil;
import redis.clients.jedis.Jedis;

/**
 * 发布消息
 */
public class PublishTest {
    
    public static void main(String[] args) {
    
        Jedis jedis = JedisUtil.getJedis();
        // 发布消息
        jedis.publish("news-sport", "fpx win the lol world championship");
        jedis.publish("news-technology", "auto programing robot was born");
    }

}
