package com.zk;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 
 * @author cck
 */
public class ZkDemo {
    
    public static void main(String[] args) {
        
        // 建立连接
        try {
            
            CountDownLatch downLatch = new CountDownLatch(1);
            
            // 参数1是zk服务器的地址，如果有多个就用','号分开  如：ip:port,ip:port
            // 参数2是会话过期时间
            // 参数3是watcher,
            ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 4000, new Watcher() {
                
                @Override
                public void process(WatchedEvent event) {
                    
                    // 如果连接成功，会收到响应事件
                    if (Event.KeeperState.SyncConnected == event.getState()) {
                        downLatch.countDown();
                    }
                }
            });
            
            downLatch.await();
            
            // 查看zk状态
            System.out.println(zk.getState());  // CONNECTED
            
            // 增删改查
            
            // 增
            // 参数1 路径 参数2 值
            // 参数3 ACL 权限
            // 参数4 创建模式，例子中创建的是持久化节点
            zk.create("/cck-service", "0".getBytes(),
                    Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
            Thread.sleep(1000);
            
            // 查  节点值
            Stat stat = new Stat();
            byte[] data = zk.getData("/cck-service", null, stat);
            System.out.println(new String(data));
            
            // 改  节点值
            zk.setData("/cck-service", "1".getBytes(), stat.getVersion());
            Thread.sleep(1000);
            
            stat = new Stat();
            data = zk.getData("/cck-service", null, stat);
            System.out.println(new String(data));
            
            // 删
            zk.delete("/cck-service", stat.getVersion());
            
            zk.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
}
