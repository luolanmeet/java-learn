package pers;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class ClusterTest {
    
    public static void main(String[] args) {
        
        // 使用集群部署方式
        Config config = new Config();
        config.setCodec(new org.redisson.client.codec.StringCodec());
    
        config.useClusterServers()
                // 集群状态扫描间隔时间，单位是毫秒
                .setScanInterval(2000)
                // cluster方式至少6个节点(3主3从，3主做分片，3从保证高可用)
                .addNodeAddress("redis://192.168.8.207:7291" )
                .addNodeAddress("redis://192.168.8.207:7292")
                .addNodeAddress("redis://192.168.8.207:7293")
                .addNodeAddress("redis://192.168.8.207:7294")
                .addNodeAddress("redis://192.168.8.207:7295")
                .addNodeAddress("redis://192.168.8.207:7296");
    
        RedissonClient redissonClient = Redisson.create(config);
        redissonClient.shutdown();
    }

}
