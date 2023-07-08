package pers.thread;

import java.util.HashMap;
import java.util.Map;

/**
 * 线程变量工具
 * @auther ken.ck
 * @date 2023/7/6 22:10
 */
public class ThreadVariableTool {

    private static ThreadLocal<Map<String, Object>> THREAD_LOCAL
            = ThreadLocal.withInitial(HashMap::new);

    /**
     * 设置变量
     * @param key
     * @param value
     */
    public static void add(String key, Object value) {
        THREAD_LOCAL.get().put(key, value);
    }

    /**
     * 释放变量
     * @param key
     */
    public static void release(String key) {
        THREAD_LOCAL.get().remove(key);
    }

    /**
     * 获取变量
     * @param key
     * @return
     */
    public static Object get(String key) {
        return THREAD_LOCAL.get().get(key);
    }

    /**
     * 获取变量
     * @param key
     * @return
     */
    public static String getString(String key) {
        Object val = THREAD_LOCAL.get().get(key);
        if (val != null) {
            return val.toString();
        }
        return null;
    }

    /**
     * 获取变量
     * @param key
     * @return
     */
    public static Integer getInteger(String key) {
        Object val = THREAD_LOCAL.get().get(key);
        if (val instanceof Integer) {
            return (Integer) val;
        }
        return null;
    }

    /**
     * 获取变量
     * @param key
     * @return
     */
    public static Long getLong(String key) {
        Object val = THREAD_LOCAL.get().get(key);
        if (val instanceof Long) {
            return (Long) val;
        }
        return null;
    }

}
