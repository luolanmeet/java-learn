package pers.jmm.falseSharing;

/**
 * 验证缓存行，伪共享
 */
public class DemoOne {

    public static class T {
        volatile long x;
    }

    T[] ts = new T[2];

    public void method() throws InterruptedException {

        ts[0] = new T();
        ts[1] = new T();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000000; i++)
                ts[0].x = i;
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000000; i++)
                ts[1].x = i;
        });

        long start = System.currentTimeMillis();
        t1.start();t2.start();
        t1.join();t2.join();

        System.out.println("use time : " + (System.currentTimeMillis() - start));
    }

    public static void main(String[] args) throws InterruptedException {
        new DemoOne().method();
    }

}
