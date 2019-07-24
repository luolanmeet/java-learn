package pers.demo;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 利用Future模拟超时短路
 * @author cck
 */
public class FutureDemo {

    public static void main(String[] args) {

        ExecutorService service = Executors.newFixedThreadPool(1);
    
        Random random = new Random();
    
        Future<String> future = service.submit(() -> {
            int time = random.nextInt(200);
            Thread.sleep(time);
            System.out.println("need time:" + time);  // 这里还是会执行
            return "hello";
        });
    
        try {
            String str = future.get(100, TimeUnit.MILLISECONDS); // 超时后，业务逻辑没有中断
            System.out.println(str);
        } catch (Exception e) {
            System.out.println("Fault");
            e.printStackTrace();
        }
    
        service.shutdown();
    }

}
