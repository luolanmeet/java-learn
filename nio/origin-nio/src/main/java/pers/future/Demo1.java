package pers.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @auther ken.ck
 * @date 2024/10/26 21:11
 */
public class Demo1 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Thread.currentThread().setName("m");

        CompletableFuture<Integer> async = CompletableFuture.supplyAsync(() -> {
            System.out.println("f1 " + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 1;
        });

        Thread.sleep(1000);

        async.thenApply(v -> {
            System.out.println("f2 " + Thread.currentThread().getName());
            return 1;
        });

        System.out.println("main " + Thread.currentThread().getName());

        Thread.sleep(5000);
    }

}
