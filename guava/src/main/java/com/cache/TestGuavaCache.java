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

//        testBase();
//        testTTL();
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
