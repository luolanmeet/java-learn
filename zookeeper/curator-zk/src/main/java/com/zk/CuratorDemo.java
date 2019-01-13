package com.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * 使用 curator
 * @author cck
 */
public class CuratorDemo {
    
    public static void main(String[] args) throws Exception {
        
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3)) // 设置重试
                .namespace("curator")
                .build();
        
        curatorFramework.start();
        
        // 增
        curatorFramework.create()
            .creatingParentsIfNeeded()     // 原生的zk，创建子节点需要要父节点存在才能创建成功
            .withMode(CreateMode.EPHEMERAL)
            .forPath("/cck-curator", "1".getBytes());  // "/curator/cck-curator" --> namespace/path
        
        // 查
        Stat stat = new Stat();
        byte[] bytes = curatorFramework
                .getData()
                .storingStatIn(stat)
                .forPath("/cck-curator");
        System.out.println(new String(bytes));
        
        // 改
        curatorFramework
            .setData()
            .withVersion(stat.getVersion())
            .forPath("/cck-curator", "2".getBytes());
        
        // 删
        curatorFramework.delete().forPath("/cck-curator");
        
        curatorFramework.close();
    }
}
