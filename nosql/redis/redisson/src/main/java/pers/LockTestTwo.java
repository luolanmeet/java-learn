package pers;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * 与lockTest一起测试锁竞争
 */
public class LockTestTwo {
    
    public static void main(String[] args) throws InterruptedException {
    
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.168.128:6379");
        RedissonClient redissonClient = Redisson.create(config);

        RLock lock = redissonClient.getLock("lockKey");
        if (lock.tryLock(10, 100, TimeUnit.SECONDS)) {
            System.out.println("获取锁成功");
            lock.unlock();
        } else {
            System.out.println("获取锁失败");
        }

        redissonClient.shutdown();
    }

}
