package pers.thread.printdata;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @auther ken.ck
 * @date 2023/11/4 23:21
 */
public class WithLock {

    private int num;

    private Lock lock = new ReentrantLock();

    private void print(int tIndex) {
        while (num < 100) {
            lock.lock();
            if (num % 3 == tIndex) {
                System.out.println(Thread.currentThread().getName() + " " + num);
                num++;
            }
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        WithLock obj = new WithLock();
        new Thread(() -> {
            obj.print(0);
        }, "a").start();
        new Thread(() -> {
            obj.print(1);
        }, "b").start();
        new Thread(() -> {
            obj.print(2);
        }, "c").start();
    }


}
