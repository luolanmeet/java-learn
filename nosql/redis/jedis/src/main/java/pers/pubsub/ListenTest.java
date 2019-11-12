package pers.pubsub;

import pers.util.JedisUtil;
import redis.clients.jedis.Jedis;

/**
 * 订阅消息
 */
public class ListenTest {
    
    public static void main(String[] args) {
        
        Jedis jedis = JedisUtil.getJedis();
    
        MyListener listener = new MyListener();
        // 订阅消息，会阻塞
        jedis.psubscribe(listener, new String[]{"news-*"});
    }
    
}
