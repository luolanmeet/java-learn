package pers.transaction;

import pers.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * redis事务的特点
 *  1. 操作入队，之后按照顺序执行
 */
public class TransactionTest {
    
    public static void main(String[] args) {
        testOne();
        testTwo();
    }
    
    private static void testTwo() {
    
        Jedis jedis = JedisUtil.getJedis();
    
        String balanceOne = "balanceOne";
        String balanceTwo = "balanceTwo";
    
        jedis.set(balanceOne, "1000");
        jedis.set(balanceTwo, "1000");
    
        // 开启事务
        Transaction multi = jedis.multi();
    
        // 进入队列
        multi.incrBy(balanceOne, 100);
        // balanceTwo变成字符串，decrBy的时候会报错
        multi.append(balanceTwo, "aaa");
        multi.decrBy(balanceTwo, 100);
    
        // 执行事务
        multi.exec();
    
        System.out.println(jedis.get(balanceOne));
        System.out.println(jedis.get(balanceTwo));
    }
    
    /**
     * 正常的使用
     */
    private static void testOne() {
    
        Jedis jedis = JedisUtil.getJedis();
    
        String balanceOne = "balanceOne";
        String balanceTwo = "balanceTwo";
    
        jedis.set(balanceOne, "1000");
        jedis.set(balanceTwo, "1000");
    
        // 开启事务
        Transaction multi = jedis.multi();
    
        // 进入队列
        multi.incrBy(balanceOne, 100);
        multi.decrBy(balanceTwo, 100);
    
        // 执行事务
        multi.exec();
    
        System.out.println(jedis.get(balanceOne));
        System.out.println(jedis.get(balanceTwo));
    }
    
}
