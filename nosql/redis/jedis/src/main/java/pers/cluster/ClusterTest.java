package pers.cluster;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * redis 集群
 * 需要改redis服务配置
 */
public class ClusterTest {
    
    static final ResourceBundle redisProperties = ResourceBundle.getBundle("redis");
    static final String HOST = redisProperties.getString("redis.host");
    static final int PORT = Integer.valueOf(redisProperties.getString("redis.port"));
    
    public static void main(String[] args) {
    
        HostAndPort hostAndPort = new HostAndPort(HOST, PORT);
        // ...
    
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(hostAndPort);
    
        JedisCluster cluster = new JedisCluster(nodes);
        
        cluster.set("cck", "atman");
        System.out.println(cluster.get("cck"));
    }

}
