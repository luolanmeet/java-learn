package com.collection;

import com.google.common.collect.HashBasedTable;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * 是一个表的结构，拥有行列的概念，可以用来作为计分板
 */
public class TestTable {

    @Test
    public void test1() {

        HashBasedTable<String, Integer, Integer> table = HashBasedTable.create();

        table.put("cck", 1, 70);
        table.put("cck", 2, 60);
        table.put("cck", 3, 40);

        table.put("ck", 1, 55);
        table.put("ck", 2, 56);
        table.put("ck", 3, 57);

        // 获取行数据
        Map<Integer, Integer> cckScore = table.row("cck");
        cckScore.entrySet().forEach(System.out::println);
        System.out.println();

        // 获取列数据
        Map<String, Integer> column = table.column(1);
        column.entrySet().forEach(System.out::println);

    }

}
