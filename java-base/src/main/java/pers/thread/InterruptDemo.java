package pers.thread;

import java.util.concurrent.TimeUnit;

/**
 * 线程中断
 * @auther ken.ck
 * @date 2024/5/15 02:04
 */
public class InterruptDemo implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new InterruptDemo());
        thread.start();
        TimeUnit.SECONDS.sleep(2);
        thread.interrupt();
    }

    @Override
    public void run() {
        // 可正常退出
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println(Thread.currentThread().getName());

            // 主动把当前线程复位，相当于中断信号变为 false
//            Thread.interrupted();
        }

        // 中断复位 无法正常退出
        while (!Thread.currentThread().isInterrupted()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
                // jvm 会中断复位。true -> false

                // 可以给自己发中断信号 false -> true
//                 Thread.currentThread().interrupt();
            }
            System.out.println(Thread.currentThread().getName());
        }

    }
}
