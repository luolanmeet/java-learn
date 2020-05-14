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

    public static void main(String[] args) {

        testBase();

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
