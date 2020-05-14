package com.stream.limit;

import com.google.common.util.concurrent.RateLimiter;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 接口限流 -- 漏桶算法
 * @author cck
 */
public class TestSmoothWarmingUp {
    
    // 一秒钟生产5个令牌
    RateLimiter rateLimiter = RateLimiter.create(5, 1000, TimeUnit.MILLISECONDS);
    
    // 接收并处理请求
    public void doRequest() {
    
        String threadName = Thread.currentThread().getName();
    
        // 尝试获取令牌
        if (rateLimiter.tryAcquire()) {
        
            System.out.println(threadName + " --> 处理成功");
        } else {
        
            System.out.println(threadName + " --> 处理失败");
        }
    }
    
    public static void main(String[] args) throws IOException {
    
        CountDownLatch countDownLatch = new CountDownLatch(1);
        TestSmoothWarmingUp smoothWarmingUp = new TestSmoothWarmingUp();
    
        Random random = new Random();
    
        for (int i = 0; i < 20; i++) {
        
            new Thread(() -> {
                try {
                    countDownLatch.await();
                    Thread.sleep(100 * random.nextInt(10));
                    smoothWarmingUp.doRequest();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "r" + i).start();;
        }
        countDownLatch.countDown();
        System.in.read();
    }
    
}
