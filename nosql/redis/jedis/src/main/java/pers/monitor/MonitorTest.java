package pers.monitor;

import pers.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisMonitor;

/**
 * Redis Monitor 命令用于实时打印出 Redis 服务器接收到的命令，调试用。
 */
public class MonitorTest {
    
    public static void main(String[] args) {
        
        Jedis jedis = JedisUtil.getJedis();
        
        jedis.monitor(new JedisMonitor() {
            @Override
            public void onCommand(String command) {
                System.out.println(command);
            }
        });
        
    }

}
