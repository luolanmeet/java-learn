package pers.transaction;

import pers.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * redis事务
 * https://redis.io/topics/transactions/
 *
 * 为redis事务增加 CAS 乐观锁行为
 */
public class TransactionWatchTest {
    
    public static void main(String[] args) throws IOException {
    
        final String trxkey = "trxkey";
        String otherKey = "otherKey";
    
        Jedis tmp = JedisUtil.getJedis();
        tmp.del(trxkey);
        tmp.del(otherKey);
        
        new Thread(() -> {
    
            Jedis jedis = JedisUtil.getJedis();
    
            String watch = jedis.watch(trxkey);
            System.out.println("线程1 watch结果：" + watch);
            
            // 开启事务
            Transaction multi = jedis.multi();
            
            // 对数据进行修改的操作，操作会进入队列中
            multi.set(trxkey, "thread-one");
            multi.set(otherKey, "thread-one");
    
            // 确保线程2已经修改了trxkey的值
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    
            // 执行队列中的所有命令， multi.discard() 则是放弃
            List<Object> result = multi.exec();
            System.out.println("线程1 exec执行结果：" + result);
            
            jedis.unwatch();
        }).start();
    
        new Thread(() -> {
    
            // 确保线程1已经做了watch 操作
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            Jedis jedis = JedisUtil.getJedis();
            String result = jedis.set(trxkey, "thread-two");
            System.out.println("线程2 set执行结果：" + result);
        }).start();
        
        System.in.read();
        System.out.println(tmp.get(trxkey));
        System.out.println(tmp.get(otherKey));
        JedisUtil.close();
    }

}
