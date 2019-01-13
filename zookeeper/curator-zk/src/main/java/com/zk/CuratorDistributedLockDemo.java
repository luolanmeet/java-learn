package com.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 使用curator封装zk后提供的分布式锁 
 * @author cck
 */
public class CuratorDistributedLockDemo {
    
    public static void main(String[] args) {
        
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3)) // 设置重试
                .namespace("curator")
                .build();
        
        curatorFramework.start();
        
        InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFramework, "/locks");
        
        try {
            interProcessMutex.acquire();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
