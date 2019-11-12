package pers.lua;

import pers.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.Arrays;

/**
 * redis 调用lua脚本 --> eval lua-script key-num [key1 key2 ..] [value1 value2 ..]
 * lua脚本中使用redis命令 --> redis.call(command, key [param1, param2 ..])
 */
public class LuaTest {
    
    public static void main(String[] args) {
        
        Jedis jedis = JedisUtil.getJedis();
        
        test(jedis);
        
        for (int i = 0; i < 10; i++) {
            limit(jedis);
        }
    }
    
    /**
     * 简单使用
     */
    public static void test(Jedis jedis) {
        
        jedis.del("cck");
    
        // lua 脚本
        String script = "return redis.call('set', KEYS[1], ARGV[1])";
    
        // 脚本，参数数量，参数，参数
        // 对应到在redis客户端中执行 eval "return redis.call('set',KEYS[1],ARGV[1])" 1 cck atman
        Object result = jedis.eval(script, 1, "cck", "atman");
    
        System.out.println(result);
        System.out.println(jedis.get("cck"));
    }
    
    /**
     * 限制访问redis次数
     */
    public static void limit(Jedis jedis){
    
        String lua = "local num = redis.call('incr', KEYS[1]) " +
                "if tonumber(num) == 1 then " +
                    "redis.call('expire', KEYS[1], ARGV[1]) " +
                    "return 1 " +
                "elseif tonumber(num) > tonumber(ARGV[2]) then " +
                    "return 0 " +
                "else " +
                    "return 1 " +
                "end";
        
        // 10s内一个ip最多允许请求5次
        Object result = jedis.eval(lua, Arrays.asList("localhost"), Arrays.asList("10", "5"));
        System.out.println(result);
    }
    
}
