package pers.pipeline;

import pers.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 使用pipeline提高吞吐量
 * 发送命令 -> 命令排队 -> 命令执行 -> 返回结果，
 * 发送命令和返回结果称为往返时间(RTT)
 */
public class PipelineGetTest {
    
    public static void main(String[] args) {
    
        new Thread(() -> {
    
            long t1 = System.currentTimeMillis();
    
            Jedis jedis = JedisUtil.getJedis();
            // 获取匹配的所有key
            Set<String> keys = jedis.keys("batch*");
            List<String> result = new ArrayList<>();
            for (String key : keys) {
                result.add(jedis.get(key));
            }
            
            long t2 = System.currentTimeMillis();
            System.out.println("直接获取耗时: " + (t2 - t1) + " 毫秒");
        }).start();
    
        new Thread(() -> {
    
            long t1 = System.currentTimeMillis();
            
            Jedis jedis = JedisUtil.getJedis();
            Set<String> keys = jedis.keys("batch*");
            Pipeline pipelined = jedis.pipelined();
            for (String key : keys) {
                pipelined.get(key);
            }
            List<Object> result = pipelined.syncAndReturnAll();
    
            long t2 = System.currentTimeMillis();
            System.out.println("使用pipelined获取耗时: " + (t2 - t1) + " 毫秒");
        }).start();
    
    }

}
