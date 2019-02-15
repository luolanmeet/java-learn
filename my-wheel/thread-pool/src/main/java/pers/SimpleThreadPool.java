package pers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 利用阻塞队列存放需要执行的 任务
 * 使用thread数组，在run方法中阻塞获取任务并执行任务
 * @author cck
 */
public class SimpleThreadPool {
    
    private final static int DEFAULT_THREAD_POOL_SIZE = 10;
    
    private final int size;
    
    private List<Thread> threadPools;
    
    private BlockingQueue<Runnable> tasks;
    
    public SimpleThreadPool() {
        this(DEFAULT_THREAD_POOL_SIZE);
    }
    
    public SimpleThreadPool(int size) {
        
        this.size = size;
        this.threadPools = new ArrayList<>(size);
        this.tasks = new LinkedBlockingQueue<>();
        
        // 构造线程，每个线程从阻塞队列中获取任务执行
        for (int i = 0; i < size; i++) {
            
            Thread thread = new Thread(()-> {
                
                try {
                    
                    while (true) {
                        Runnable task = tasks.take();
                        System.out.println(Thread.currentThread().getName() + " handle task");
                        task.run();
                    }
                    
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            
            String threadName = "stp-" + i;
            thread.setName(threadName);
            thread.start();
            threadPools.add(thread);
        }
    }
    
    /**
     * 提交任务
     * @param task
     */
    public void submit(Runnable task) {
        tasks.offer(task);
    }
    
    public int getPoolSize() {
        return this.size;
    }
    
}
