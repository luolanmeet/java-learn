package pers.jmm.outOfOrder;

/**
 * 验证cpu乱序执行
 *
 * as-if-serial只适用于单线程，因此在多线程可以验证cpu乱序执行，也就是指令重排。
 * http://www.cs.umd.edu/users/pugh/java/memoryModel/jsr-133-faq.html
 */
public class DemoTwo {

    private int a = 0, b = 0, x = 0, y = 0;

    private void method() throws InterruptedException {

        int time = 0;

        while (true) {

            Thread t1 = new Thread(() -> {
                a = 1;
                b = 1;
            });

            // 如果没有指令重排，那么x=1时 y也需要等于1
            Thread t2 = new Thread(() -> {
                x = a;
                y = b;
            });

            t1.start();t2.start();
            t1.join();t2.join();

            System.out.println(x + " " + y);

            time++;
            if (x == 1 && y == 0) {
                System.out.println("第" + time + "次出现 x=1 y=0");
                break;
            }
            a = 0; b = 0; x = 0; y = 0;
        }

    }

    public static void main(String[] args) throws InterruptedException {
        new DemoTwo().method();
    }

}
