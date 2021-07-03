package com.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 *
 */
public class TestGuavaCache {

    static Integer userId = 1;
    static User user = User.builder().id(userId).name("cck").build();

    public static void main(String[] args) throws InterruptedException {
//        testDateChange();
//        testBase();
//        testTTL();
        testTTL2();
    }

    private static void testDateChange() throws InterruptedException {

        // 如果作为全局缓存，应该要考虑到数据可能会被修改，相同的如ehcache
        Cache<Integer, User> cache = CacheBuilder.newBuilder().build();
        cache.put(userId, user);

        Thread t1 = new Thread(() -> {
            User tmpUser = cache.getIfPresent(userId);
            tmpUser.setName(tmpUser.getName() + 1);
            System.out.println(tmpUser);
        });
        Thread t2 = new Thread(() -> {
            User tmpUser = cache.getIfPresent(userId);
            tmpUser.setName(tmpUser.getName() + 2);
            System.out.println(tmpUser);
        });

        t1.start();t2.start();
        t1.join();t2.join();
        System.out.println(cache.getIfPresent(userId));
    }

    private static void testTTL() {
        // 设置大小
        Cache<Integer, User> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(3, TimeUnit.SECONDS)  // 添加后3秒过期
                .maximumSize(100).build(); // 最大100个,到达最大容量后，先进缓存的数据会先失效
        for (int i = 0; i < 1000; i++) {
            cache.put(i, user);
        }
        System.out.println(cache.size());
    }

    private static void testTTL2() throws InterruptedException {

        // 设置大小
        Cache<Integer, User> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(3, TimeUnit.SECONDS) // 3秒过期
                .build();

        cache.put(userId, User.builder().id(userId).name("cck2").build());

        new Thread(() -> {
            User user = cache.getIfPresent(userId);
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println("t- " + user);
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        TimeUnit.SECONDS.sleep(4);
        System.out.println(cache.size()); // 不get一下，过期了数量还是不会减少
        System.out.println(cache.getIfPresent(userId));
        System.out.println(cache.size());
    }

    private static void testBase() {

        Cache<Integer, User> cache = CacheBuilder.newBuilder().build();

        System.out.println(cache.getIfPresent(userId));

        cache.put(userId, user);
        System.out.println(cache.getIfPresent(userId));

        // 让所有缓存失效
        cache.invalidateAll();
        System.out.println(cache.getIfPresent(userId));

        System.out.println("=====================");
    }

    @Data
    @Builder
    static class User {
        private Integer id;
        private String name;
    }

}
