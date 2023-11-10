package pers.thread.printdata;

import java.util.concurrent.Semaphore;

/**
 * @auther ken.ck
 * @date 2023/11/4 23:56
 */
public class WithSemaphore {

    private int num;
    private final static Semaphore s1 = new Semaphore(1);
    private final static Semaphore s2 = new Semaphore(0);
    private void print(Semaphore currentS, Semaphore nextS) {
        while (num < 9) {
            try {
                currentS.acquire();
                System.out.println(Thread.currentThread().getName() + " " + num);
                num--;
                nextS.release();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        WithSemaphore obj = new WithSemaphore();
        new Thread(()->{
            obj.print(s1, s2);
        }, "a").start();
        new Thread(()->{
            obj.print(s2, s1);
        }, "b").start();
    }

}
