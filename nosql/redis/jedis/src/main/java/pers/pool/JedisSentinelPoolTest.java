package pers.pool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * 使用了Redis哨兵时的使用
 * 主从 读写分离
 */
public class JedisSentinelPoolTest {
    
    public static void main(String[] args) {
        
        // master的名字是sentinel.conf配置文件里面的名称
        String masterName = "redis-master";
        Set<String> sentinels = new HashSet<>();
        sentinels.add("192.168.8.203:26379");
        sentinels.add("192.168.8.204:26379");
        sentinels.add("192.168.8.205:26379");
        JedisSentinelPool pool = new JedisSentinelPool(masterName, sentinels);
    
        Jedis jedis = pool.getResource();
        
        jedis.set("cck", "atman");
        System.out.println(jedis.get("cck"));
    }

}
