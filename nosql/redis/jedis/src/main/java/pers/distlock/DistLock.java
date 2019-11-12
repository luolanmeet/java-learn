package pers.distlock;

import pers.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * redis分布式锁
 * 过期时间的值要设成什么？
 */
public class DistLock {
    
    private static final String LOCK_SUCCESS = "OK";
    private static final Long RELEASE_SUCCESS = 1L;
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    
    /**
     * 尝试获取分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识，这个标识必须全局唯一，并且之后都不会再使用到
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {
    
        // set命令， key value NX(不存在时存入 XX:存在时存入) PX（毫秒过期时间单位 EX:秒） 过期时间
        String result = jedis.set(
                lockKey, requestId, SET_IF_NOT_EXIST,
                SET_WITH_EXPIRE_TIME, expireTime);
    
        return LOCK_SUCCESS.equals(result);
    }
    
    /**
     * 释放分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {
       
        // 使用lua脚本，不能直接使用del,requestId要求全局唯一，确保真正持有锁的线程才能进行删除
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        
        return RELEASE_SUCCESS.equals(result);
    
        /** 不能直接使用del的原因
            举个例子
                A客户端拿到对象锁，但在因为一些原因被阻塞导致无法及时释放锁。
                因为过期时间已到，Redis中的锁对象被删除。
                B客户端请求获取锁成功。
                A客户端此时阻塞操作完成，删除key释放锁。(1)
                C客户端请求获取锁成功。
                这时B、C都拿到了锁，因此分布式锁失效。
            
            lua脚本版，要求lockKey的值requestId全局唯一,这样就能阻止(1)步操作
         */
    }
    
    public static void main(String[] args) {
        
        Jedis jedis = JedisUtil.getJedis();
        
        String lockKey = "DistLock";
        String requestId = System.currentTimeMillis() + "";
    
        System.out.println(tryGetDistributedLock(jedis, lockKey, requestId, 5000));
        System.out.println(tryGetDistributedLock(jedis, lockKey, requestId, 5000));
    
        System.out.println(releaseDistributedLock(jedis, lockKey, requestId + "a"));
        System.out.println(releaseDistributedLock(jedis, lockKey, requestId));
    }
    
}
