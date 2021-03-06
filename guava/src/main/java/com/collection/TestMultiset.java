package com.collection;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 *
 * 可用于统计，替换以下逻辑
 * Map<String, Integer> counts = new HashMap<>();
 * for (String word : words) {
 *     Integer count = counts.get(word);
 *     if (count == null) {
 *         counts.put(word, 1);
 *     } else {
 *         counts.put(word, count + 1);
 *     }
 * }
 *
 */
public class TestMultiset {

    @Test
    public void test1() {

        Multiset<String> multiset = HashMultiset.create();

        String name1 = "cxc";
        String name2 = "cx";
        String name3 = "cts";
        String name4 = "cck";

        // add 计数加1， remove 计数减一
        multiset.add(name1);
        multiset.add(name1);
        multiset.remove(name1);

        // 返回指定对象的计数
        Assertions.assertEquals(1, multiset.count(name1));

        // 设置对象计数为3
        multiset.setCount(name1, 3);
        Assertions.assertEquals(3, multiset.count(name1));

        multiset.add(name2);
        multiset.add(name2);
        // 在原有基础上增加计数
        multiset.add(name2, 2);
        Assertions.assertEquals(4, multiset.count(name2));

        multiset.add(name3);
        multiset.add(name3);
        multiset.add(name3);
        multiset.add(name3);

        // 查看没add过的， 0
        Assertions.assertEquals(0, multiset.count(name4));

        // 返回总计数
        Assertions.assertEquals(11, multiset.size());

        // 返回计数的对象
        Set<String> names = multiset.elementSet();
        System.out.println(names);

    }

}
