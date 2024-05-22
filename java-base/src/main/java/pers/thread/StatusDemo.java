package pers.thread;

import java.util.concurrent.TimeUnit;

/**
 * 线程状态
 * 运行程序 -> jps 查看 pid -> jstack pid 打印堆栈信息
 * @auther ken.ck
 * @date 2024/5/15 01:24
 */
public class StatusDemo {

    public static void main(String[] args) {
        // TIMED_WAITING (sleeping)
        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "timedwaiting").start();

        // 线程在 ThreadStatusDemo 类锁上通过 wait 进行等待
        // WAITING (on object monitor)，阻塞在对象监视器上
        new Thread(() -> {
            synchronized (StatusDemo.class) {
                try {
                    StatusDemo.class.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "waiting").start();

        // BLOCKED 线程在BlockedDemo加锁后，不会释放锁
        // 有一个线程状态是 TIMED_WAITING (sleeping)
        // 另一个是 BLOCKED (on object monitor)，阻塞在对象监视器上
        new Thread(new BlockedDemo(), "blocked-1").start();
        new Thread(new BlockedDemo(), "blocked-2").start();
    }

    static class BlockedDemo implements Runnable {
        @Override
        public void run() {
            synchronized (BlockedDemo.class) {
                // 获得锁之后不释放
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

}
