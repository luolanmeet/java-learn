package com;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Builder;
import lombok.Data;

/**
 *
 */
public class TestGuavaCache {

    static Integer userId = 1;
    static User user = User.builder().id(userId).name("cck").build();

    public static void main(String[] args) {

        testBase();

        // 设置大小
        Cache<Integer, User> cache = CacheBuilder.newBuilder().maximumSize(100).build();
        for (int i = 0; i < 10000; i++) {
            cache.put(i, user);
        }
        System.out.println(cache.size());
    }

    private static void testBase() {
        System.out.println("=====================");
        Cache<Integer, User> cache = CacheBuilder.newBuilder().build();

        System.out.println(cache.getIfPresent(userId));

        cache.put(userId, user);
        System.out.println(cache.getIfPresent(userId));

        // 让所有缓存失效
        cache.invalidateAll();
        System.out.println(cache.getIfPresent(userId));
    }

    @Data
    @Builder
    static class User {
        private Integer id;
        private String name;
    }

}
