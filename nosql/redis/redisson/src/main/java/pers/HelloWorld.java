package pers;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class HelloWorld {
    
    public static void main(String[] args) {
    
        // 使用单节点部署方式
        Config config = new Config();
        config.useSingleServer().setAddress("redis://117.48.228.179:6379");
        RedissonClient redissonClient = Redisson.create(config);
    
        RBucket<String> bucket = redissonClient.getBucket("cck");
        // 设值时，服务器的值也改变
        bucket.set("atman");
        System.out.println(bucket.get());
    
        bucket.set("atman222");
        bucket = redissonClient.getBucket("cck");
        System.out.println(bucket.get());
        
        redissonClient.shutdown();
    }

}
