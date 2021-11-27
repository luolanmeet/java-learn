package pers.cpu.outOfOrder;

/**
 * 验证cpu乱序执行
 *
 * as-if-serial只适用于单线程，因此在多线程可以验证cpu乱序执行，也就是指令重排。
 * happen-before原则
 *      程序的顺序规则（一个线程内）
 *      volatile规则
 *      传递性规则
 *      start规则
 *      join规则
 *      锁规则
 */
public class DemoOne {

    private int a = 0, b = 0, c = 2;

    private void method() throws InterruptedException {

        int time = 0;

        while (true) {

            Thread t1 = new Thread(() -> {
                a = 1;
                b = 1;
            });

            // 如果没有发生指令重排，
            // 那么 b的值为1时， a一定也被赋值为1，对应c应该被赋值为1.
            // 如果出现 c=0，则证明出现指令重排。
            Thread t2 = new Thread(() -> {
                if (b == 1) {
                    c = a;
                }
            });

            t1.start();t2.start();
            t1.join();t2.join();

            System.out.println(a + " " + b + " " + c);

            time++;
            if (c == 0) {
                System.out.println("第" + time + "次出现 a=" + a + " b=" + b + " c=" + c);
                break;
            }
            a = 0; b = 0; c = 2;
        }

    }

    public static void main(String[] args) throws InterruptedException {
        new DemoOne().method();
    }

}
