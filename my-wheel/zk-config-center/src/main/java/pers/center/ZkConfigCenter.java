package pers.center;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import pers.config.ConfigHolder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author cck
 */
@Slf4j
public class ZkConfigCenter {
    
    /** 根节点名 */
    private final static String ROOT_NODE_NAME = "zk-config-center";
    
    /** zk地址 */
    private final String address;
    
    /** Curator实例 */
    private CuratorFramework curatorFramework;
    
    private Map<String, ConfigHolder> configHolderMap = new ConcurrentHashMap<>();
    
    public ZkConfigCenter(String address) {
        
        this.address = address;
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(address)
                .sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace(ROOT_NODE_NAME)
                .build();
    
        curatorFramework.start();
    }
    
    public <T> T getConfig(String configName, Class<T> clazz) {
    
        ConfigHolder configHolder = configHolderMap.get(configName);
    
        if (configHolder != null) {
            return (T) configHolder.getConfig();
        }
        
        try {
            configHolder = new ConfigHolder(clazz, configName, curatorFramework);
            configHolderMap.put(configName, configHolder);
            return (T) configHolder.getConfig();
        } catch (Exception e) {
            log.error("e[{}]", e);
        }
    
        return null;
    }
    
}
