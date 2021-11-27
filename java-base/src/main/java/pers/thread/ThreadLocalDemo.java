package pers.thread;

import java.io.IOException;

/**
 * ThreadLocal
 * @author cck
 * @date 2020/12/24 23:44
 */
public class ThreadLocalDemo {

    private static ThreadLocal<Integer> NUM_ONE = new ThreadLocal<>();

    /**
     * 继承父线程变量的 ThreadLocal
     */
    private static ThreadLocal<Integer> NUM_TWO = new InheritableThreadLocal<>();

    public static void main(String[] args) throws IOException {

        NUM_ONE.set(111);
        NUM_TWO.set(222);

        new Thread(() -> {
                System.out.println(NUM_ONE.get());
                System.out.println(NUM_TWO.get());
            }
        ).start();

        System.in.read();
    }

}
