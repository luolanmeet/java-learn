package com.collection.map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Multimap  Map<K, Collection<V>>
 */
public class TestMultimap {

    @Test
    public void test1() {

        // 等价于原生的 Map<Integer, List<String>>
        Multimap<Integer, String> arrayListMultimap = ArrayListMultimap.create();

        // 原生的写法
        // List<String> list = map.get(1);
        // if (list == null) map.put(1, new ArrayList<>());
        // list.add("cxx");

        // 新增
        arrayListMultimap.put(1, "cxc");
        arrayListMultimap.put(1, "cts");
        arrayListMultimap.put(1, "cx");
        arrayListMultimap.put(1, "hk");

        arrayListMultimap.put(2, "lxh");
        arrayListMultimap.put(2, "dc");
        arrayListMultimap.put(2, "lht");
        arrayListMultimap.put(2, "hk");

        arrayListMultimap.putAll(3, Arrays.asList("lrx","dwj","hjb"));
        arrayListMultimap.put(3, "xxx");

        // 查找
        Collection<String> names = arrayListMultimap.get(1);
        System.out.println(names); // [cxc, cts, cx]

        // 遍历
        arrayListMultimap.forEach((key, val) -> {
            System.out.print(key + " ");
            System.out.println(val);
        });

        // 删除
        assertTrue(arrayListMultimap.remove(3, "xxx"));

        // 存在判断
        assertTrue(arrayListMultimap.containsKey(3));
        assertFalse(arrayListMultimap.containsEntry(3, "xxx"));
        assertFalse(arrayListMultimap.containsValue("xxx"));
        assertTrue(arrayListMultimap.containsValue("hk")); // 有两个hk值

    }

}
