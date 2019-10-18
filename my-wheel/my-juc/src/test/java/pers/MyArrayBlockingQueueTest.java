package pers;

/**
 * @author cck
 */
public class MyArrayBlockingQueueTest {
    
    final static MyArrayBlockingQueue<String> queue = new MyArrayBlockingQueue<>(3);
    
    public static void main(String[] args) {
        
        Runnable takeTask = () -> {
            try {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + ": " + queue.take());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        
        Runnable putTask = () -> {
            try {
                for (int i = 0; i < 30; i++) {
                    queue.put(Thread.currentThread().getName() + " " + i);
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        
        new Thread(takeTask, "T-0").start();
        new Thread(takeTask, "T-1").start();
        new Thread(takeTask, "T-2").start();
        
        new Thread(putTask, "P-0").start();
        new Thread(putTask, "P-1").start();
    }
    
}
