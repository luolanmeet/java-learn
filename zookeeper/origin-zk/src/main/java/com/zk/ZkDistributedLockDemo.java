package com.zk;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 利用zk实现分布式锁 
 * @author cck
 */
public class ZkDistributedLockDemo implements Lock, Watcher {

    private ZooKeeper zk = null;
    private String ROOT_LOCK = "/locks"; // 定义根节点
    private String WAIT_LOCK;            // 等待前一个锁
    private String CURRENT_LOCK;         // 表示当前的锁
    
    private CountDownLatch countDownLatch;
    
    public ZkDistributedLockDemo() {
        
        try {
            zk = new ZooKeeper("127.0.0.1:2181", 4000, this);
            // 判断根节点是否存在（添加根节点的时候没加锁，会报错，用zkCli手动加上了 /locks 根节点）
            Stat stat = zk.exists(ROOT_LOCK, false);
            if (stat == null) {
                zk.create(ROOT_LOCK, "0".getBytes(),
                        Ids.OPEN_ACL_UNSAFE,
                        CreateMode.EPHEMERAL);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    // Watcher接口的方法
    @Override
    public void process(WatchedEvent event) {
        
        // 在收到监听节点的变化的时候回调这里
        if (this.countDownLatch != null) {
            this.countDownLatch.countDown();
        }
    }

    @Override
    public void lock() {
        
        // 如果获得锁成功
        if (tryLock()) {
            System.out.println(Thread.currentThread().getName() + 
                    "-->" + CURRENT_LOCK + "-->" + "获得锁成功");
            return ;
        }
        
        // 没有获得锁
        waitForLock(WAIT_LOCK);
    }

    private boolean waitForLock(String prev) {
        
        try {
            
            Stat stat = zk.exists(WAIT_LOCK, true);
            if (stat != null) {
                
                System.out.println(Thread.currentThread().getName() + 
                        "-->等待锁" + WAIT_LOCK + "释放");
                
                countDownLatch = new CountDownLatch(1);
                countDownLatch.await();
                
                System.out.println(Thread.currentThread().getName() + "-->获得锁成功");
            }
            
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    @Override
    public void lockInterruptibly() throws InterruptedException {
    }

    @Override
    public boolean tryLock() {
        
        try {
            // 创建临时有序节点
            CURRENT_LOCK = zk.create(ROOT_LOCK + "/","0".getBytes(),
                        Ids.OPEN_ACL_UNSAFE,
                        CreateMode.EPHEMERAL_SEQUENTIAL);  // 创建的是临时有序节点
            
            System.out.println(Thread.currentThread().getName() + 
                    "-->" + CURRENT_LOCK + "-->" + "尝试竞争锁");
            
            // 获取根节点下的所有子节点
            List<String> childrens = zk.getChildren(ROOT_LOCK, false);
            
            // 对节点进行排序
            TreeSet<String> treeSet = new TreeSet<>();
            for (String children : childrens) {
                treeSet.add(ROOT_LOCK + "/" + children);
            }
            
            // 获取当前所有节点中序号最小的节点（创建的是临时有序节点）
            String firstNode = treeSet.first();
            // 如果当前自己创建的是最小的节点，表示获得锁
            if (firstNode.equals(CURRENT_LOCK)) {
                return true;
            }
            
            // 不是自己最小，表示未获得锁
            
            // 获取所有比自己序号小的节点
            SortedSet<String> lessThenMe = treeSet.headSet(CURRENT_LOCK);
            if (!lessThenMe.isEmpty()) {
                // 在比自己序号小的节点集合中，获取节点序号最大的
                WAIT_LOCK = lessThenMe.last();
            }
            
        } catch (KeeperException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        
        System.out.println(Thread.currentThread().getName() + 
                "-->释放锁" + CURRENT_LOCK);
        try {
            
            zk.delete(CURRENT_LOCK, -1);
            CURRENT_LOCK = null;
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
    
}
