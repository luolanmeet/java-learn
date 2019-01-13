package com.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 使用 curator 的封装好的事件
 * 
 * 提供了三种
 *  PathChildCache 监听一个节点下子节点的创建、删除、更新 
 *  NodeCache 监听一个节点的创建、更新 
 *  TreeCache 综合前两种
 * @author cck
 */
public class CuratorWatcherDemo {

    public static void main(String[] args) throws Exception {

        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3)) // 设置重试
                .namespace("curator")
                .build();

        curatorFramework.start();

        // 当前节点的创建和删除事件监听
        addListenerWithNodeCache(curatorFramework, "/node");
        // 子节点的增加、修改、删除的事件监听
        addListenerWithPathChildCache(curatorFramework, "/node");
        // 综合节点监听事件
//        addListenerWithTreeCache(curatorFramework, "/node");
        
        System.in.read();
    }

    public static void addListenerWithTreeCache(
            CuratorFramework curatorFramework, 
            String path) throws Exception {
        
        TreeCache treeCache = new TreeCache(curatorFramework, path);
        
        TreeCacheListener treeCacheListener = new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                System.out.println(event.getType() + "->" + event.getData().getPath());
            }
        };

        treeCache.getListenable().addListener(treeCacheListener);
        treeCache.start();
    }

    /**
     * PathChildCache 监听一个节点下子节点的创建、删除、更新 
     * NodeCache 监听一个节点的更新和创建事件 TreeCache
     * 综合PatchChildCache和NodeCache的特性
     */
    public static void addListenerWithPathChildCache(
            CuratorFramework curatorFramework, 
            String path) throws Exception {

        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, path, true);
        PathChildrenCacheListener pathChildrenCacheListener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                System.out.println("Receive Event:" + event.getType());
            }
        };

        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
        pathChildrenCache.start(PathChildrenCache.StartMode.NORMAL);
    }

    public static void addListenerWithNodeCache(
            CuratorFramework curatorFramework, 
            String path) throws Exception {
        
        NodeCache nodeCache = new NodeCache(curatorFramework, path, false);
        NodeCacheListener nodeCacheListener = new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("Receive Event:" + nodeCache.getCurrentData().getPath());
            }
        };
        
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start();
    }

}
