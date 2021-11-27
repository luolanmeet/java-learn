package pers.generic;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther ken.ck
 * @date 2021/7/16 22:59
 */
public class Maps {

    public static <K, V> Map<K, V> newHashMap(K[] keys, V[] values) {

        // 检查参数非空
        if (keys == null || keys.length <= 0
                || values == null || values.length <= 0) {
            return Collections.emptyMap();
        }

        // 转化哈希映射
        Map<K, V> map = new HashMap<>();
        int length = Math.min(keys.length, values.length);
        for (int i = 0; i < length; i++) {
            map.put(keys[i], values[i]);
        }
        return map;
    }

}
