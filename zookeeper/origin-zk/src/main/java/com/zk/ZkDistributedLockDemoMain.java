package com.zk;

import java.util.concurrent.CountDownLatch;

public class ZkDistributedLockDemoMain {
    
    public static void main(String[] args) {
        
        CountDownLatch countDownLatch = new CountDownLatch(10);
        
        for (int i = 0; i < 10; i++) {
            
            new Thread(() -> {
                
                try {
                    
                    countDownLatch.await();
                    ZkDistributedLockDemo lockDemo = new ZkDistributedLockDemo();
                    lockDemo.lock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "Thread-" + i).start();
            
            countDownLatch.countDown();
        }
    }
}
