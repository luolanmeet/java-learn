package pers;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class SentinelTest {
    
    public static void main(String[] args) {
    
        // 指定使用哨兵部署方式
        Config config = new Config();
        config.setCodec(new org.redisson.client.codec.StringCodec());
    
        config.useSentinelServers()
                .setMasterName("redis-master")
                .addSentinelAddress("redis://192.168.8.203:26379")
                .addSentinelAddress("redis://192.168.8.204:26379")
                .addSentinelAddress("redis://192.168.8.205:26379");
    
        RedissonClient redissonClient = Redisson.create(config);
        redissonClient.shutdown();
    }

}
