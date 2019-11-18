package pers;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

public class LockTest {
    
    public static void main(String[] args) throws InterruptedException {
    
        Config config = new Config();
        config.useSingleServer().setAddress("redis://117.48.228.179:6379");
        RedissonClient redissonClient = Redisson.create(config);
    
        RLock lock = redissonClient.getLock("lockKey");
        
        if (lock.tryLock(100, 10, TimeUnit.SECONDS)) {
            System.out.println("获得锁成功");
        }
    
        lock.unlock();
        redissonClient.shutdown();
    }

}
