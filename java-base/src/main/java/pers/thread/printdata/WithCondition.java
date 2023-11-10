package pers.thread.printdata;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @auther ken.ck
 * @date 2023/11/4 23:28
 */
public class WithCondition {

    private int num;

    private static final Lock lock = new ReentrantLock();
    private static final Condition c1 = lock.newCondition();
    private static final Condition c2 = lock.newCondition();
    private static final Condition c3 = lock.newCondition();

    private void print(int tIndex, Condition currentC, Condition nextC) {
        while (num < 100) {
            lock.lock();
            try {
                if (num % 3 != tIndex) {
                    // 当前现场阻塞
                    currentC.await();
                }
                System.out.println(Thread.currentThread().getName() + " " + num);
                num++;
                nextC.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
         }
    }

    public static void main(String[] args) {
        WithCondition obj = new WithCondition();
        new Thread(() -> {
            obj.print(0, c1, c2);
        }, "a").start();
        new Thread(() -> {
            obj.print(1, c2, c3);
        }, "b").start();
        new Thread(() -> {
            obj.print(2, c3, c1);
        }, "c").start();
    }

}
