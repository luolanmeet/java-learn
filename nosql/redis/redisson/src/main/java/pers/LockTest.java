package pers;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

public class LockTest {
    
    public static void main(String[] args) throws InterruptedException {
    
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.168.128:6379");
        RedissonClient redissonClient = Redisson.create(config);

//        testOne(redissonClient);
        testTwo(redissonClient);

        redissonClient.shutdown();
    }

    /**
     * 同个线程内，两个锁对象获取同一把锁
     * @param redissonClient
     * @throws InterruptedException
     */
    private static void testTwo(RedissonClient redissonClient) throws InterruptedException {

        RLock lockOne = redissonClient.getLock("lockKey");
        RLock lockTwo = redissonClient.getLock("lockKey");

        if (lockOne.tryLock(10, 100, TimeUnit.SECONDS)) {

            System.out.println("lockOne 获得锁成功");
            if (lockTwo.tryLock(10, 100, TimeUnit.SECONDS)) {
                System.out.println("lockTwo 获得锁成功");
                lockTwo.unlock();
            }

            lockOne.unlock();
        }

    }

    /**
     * 一个锁对象加锁两次
     * @param redissonClient
     * @throws InterruptedException
     */
    private static void testOne(RedissonClient redissonClient) throws InterruptedException {

        RLock lock = redissonClient.getLock("lockKey");

        // 第一个参数是获取锁的等待时间，超过这个时间就获取锁失败
        // 第二个参数是获取到锁时，为锁设置的过期时间（获取到锁时设置的，每次获取到过期时间都设为这个值）

        // 同一个锁对象加两次锁，需要释放两次锁
        if (lock.tryLock(10, 100, TimeUnit.SECONDS)) {
            System.out.println("获得锁成功 one");

            if (lock.tryLock(10, 100, TimeUnit.SECONDS)) {
                System.out.println("获得锁成功 two");
                lock.unlock();
            } else {
                System.out.println("获取锁失败 two");
            }

            lock.unlock();
        } else {
            System.out.println("获取锁失败 one");
        }
    }

}
