package pers.cpu.outOfOrder;

/**
 * 验证指令重排，类的创建过程（DCL单例需要添加volatile的原因）
 * FIXME 还没实验出来
 */
public class DemoThree {

    int x;
    private DemoThree() {
        x = 3;
    }

    static DemoThree instance = null;

    static volatile boolean flag = false;

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            while (!flag) {
                if (DemoThree.instance == null) {
                    DemoThree.instance = new DemoThree();
                    System.out.println("创建对象");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            int time = 0;
            while (true) {
                if (DemoThree.instance != null) {
                    if (DemoThree.instance.x == 0) {
                        System.out.println("第" + time + "次，出现 x = 0");
                        break;
                    }
                }
                DemoThree.instance = null;
                System.out.println("释放对象");
            }

            flag = true;
        });

        t1.start(); t2.start();
        t1.join(); t2.join();
    }

}
