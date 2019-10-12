package pers;

import java.util.concurrent.TimeUnit;

/**
 * 
 * @author cck
 */
public class MyCyclicBarrierTest {

    private static Runnable barrierAction = new Runnable() {
        int runTime = 0;
        @Override
        public void run() {
            System.out.println("run time : " + ++runTime);
        }
    };
    
    static class AwaitThread extends Thread {
        MyCyclicBarrier cb;
        public AwaitThread(MyCyclicBarrier cb, String name) {
            setName(name);
            this.cb = cb;
        }
        @Override
        public void run() {
            try {
                System.out.println(getName() + " before await");
                cb.await();
                System.out.println(getName() + " after await");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
    
        MyCyclicBarrier cb = new MyCyclicBarrier(2, barrierAction);
        new AwaitThread(cb, "T-1").start();
        new AwaitThread(cb, "T-2").start();
        
        TimeUnit.SECONDS.sleep(1);
        System.out.println();
        
        // CyclicBarrier 重复使用
        new AwaitThread(cb, "T-3").start();
        new AwaitThread(cb, "T-4").start();
    }

}
