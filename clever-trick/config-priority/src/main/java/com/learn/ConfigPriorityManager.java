package com.learn;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther ken.ck
 * @date 2023/2/24 00:18
 */
public class ConfigPriorityManager<T> {

    private List<T> tmpList;
    private String[] fieldNames;
    private int[] fieldScores;

    /**
     * 关键一个配置优先级管理类，按优先级低到高传入字段
     * @param fieldNames
     */
    public ConfigPriorityManager(String ... fieldNames) {
        this.fieldNames = fieldNames;
        this.fieldScores = new int[fieldNames.length];
        // 使用二进制 001、010、100 表示字段是否存在（优先级分数）
        for (int i = 0; i < fieldScores.length; i++) {
            fieldScores[i] = i == 0 ? 1 : 2 << i - 1;
        }
        this.tmpList = new ArrayList<>();
    }

    public ConfigPriorityManager add(T t) {
        tmpList.add(t);
        return this;
    }

    public Map<Integer, List<T>> getSortedConfig() {

        try {

            Map<Integer, List<T>> configListMap = new HashMap<>();

            for (T t : tmpList) {
                Class<?> clazz = t.getClass();
                int sumScore = 0;
                for (int i = 0; i < fieldNames.length; i++) {
                    Field field = clazz.getDeclaredField(fieldNames[i]);
                    field.setAccessible(true);
                    Object fieldValue = field.get(t);
                    if (fieldValue != null) {
                        sumScore += fieldScores[i];
                    }
                }
                List<T> ts = configListMap.computeIfAbsent(sumScore, ArrayList::new);
                ts.add(t);
            }

            return configListMap;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
