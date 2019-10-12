package pers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  JDK8 CyclicBarrier 是依靠 ReentrantLock 和 Condition 实现的
 */
public class MyCyclicBarrier {
    
    // 倒数到0时， 执行传入的Runnable
    private int count;
    private final Runnable barrierCommand;
    // 保存一开始设置的数量
    private final int parties;
    
    // 依靠trip，在count不为0之前阻塞线程 trip.await()
    // 在count为0时，唤醒等待的线程 trip.signalAll()
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition trip = lock.newCondition();
    
    public MyCyclicBarrier(int parties, Runnable barrierAction) {
        this.parties = parties;
        this.count = parties;
        this.barrierCommand = barrierAction;
    }
    
    public void await() {
    
        lock.lock();
        try {
            
            int index = --count;
            // 倒数到0
            if (index == 0) {
                if (barrierCommand != null) {
                    barrierCommand.run();
                }
                nextGeneration();
                return ;
            }
            
            trip.await();
            
        } catch (InterruptedException ie) {
        } finally {
            lock.unlock();
        }
    }
    
    private void nextGeneration() {
        trip.signalAll();
        count = parties;
    }
    
}
