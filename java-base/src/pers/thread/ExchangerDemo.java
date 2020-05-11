package pers.thread;

import java.util.concurrent.Exchanger;

/**
 * exchange只能是两个线程之间，而且必须交换完数据线程才继续执行。
 */
public class ExchangerDemo {

    static Exchanger<String> exchanger = new Exchanger();

    public static void main(String[] args) {

        new Thread(() -> {
            try {
                String str = "s1";
                str = exchanger.exchange(str);
                System.out.println(Thread.currentThread().getName() + ":" + str);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(() -> {
            try {
                String str = "s2";
                str = exchanger.exchange(str);
                System.out.println(Thread.currentThread().getName() + ":" + str);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();

    }

}
