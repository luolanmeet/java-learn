package pers.thread.printdata;

/**
 * @auther ken.ck
 * @date 2023/11/4 22:59
 */
public class WithSynchronized {

    private int num;
    private static final Object lock = new Object();

    private void print(int tIndex) {
        while (num < 100) {
            synchronized (lock) {
                while (num % 3 != tIndex) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(Thread.currentThread().getName() + " " + num);
                num++;
                lock.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        final WithSynchronized obj = new WithSynchronized();
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
