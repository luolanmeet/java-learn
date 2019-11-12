package pers.pipeline;

import pers.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.List;

/**
 * 使用pipeline提高吞吐量
 * 发送命令 -> 命令排队 -> 命令执行 -> 返回结果，
 * 发送命令和返回结果称为往返时间(RTT)
 */
public class PipelineSetTest {
    
    public static void main(String[] args) {
    
        Jedis jedis = JedisUtil.getJedis();
    
        Pipeline pipelined = jedis.pipelined();
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            pipelined.set("batch" + i, "" + i );
        }
        
        List<Object> result = pipelined.syncAndReturnAll();
        long t2 = System.currentTimeMillis();
    
        System.out.println(result);
        System.out.println("总耗时: " + (t2 - t1) + " 毫秒");
    }

}
