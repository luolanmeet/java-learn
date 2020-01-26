package com.collection;

import com.google.common.collect.HashBiMap;
import org.junit.jupiter.api.Test;

/**
 * BiMap， 可以反转的 Map，可以通过 key 找 value，
 * 也可以通过 value 找 key
 */
public class TestBiMap {

    @Test
    public void test1() {
        HashBiMap<String, String> biMap = HashBiMap.create();

        biMap.put("a", "A");

        String val = biMap.get("a");
        System.out.println(val);

        // inverse 反转
        val = biMap.inverse().get("A");
        System.out.println(val);

        // 经过反转后不影响原来的map
        val = biMap.get("a");
        System.out.println(val);
    }

}
