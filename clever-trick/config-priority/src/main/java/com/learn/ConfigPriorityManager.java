package com.learn;

/**
 * @auther ken.ck
 * @date 2023/2/24 00:18
 */
public class ConfigPriorityManager<T> {

    /**
     * 关键一个配置优先级管理类，按优先级低到高传入字段
     * @param fields
     */
    public ConfigPriorityManager(String ... fields) {

        // 按照 1、2、4、7

        for (int i = 0; i < fields.length; i++) {

        }

    }

    public ConfigPriorityManager add(T t) {
        return this;
    }

}
