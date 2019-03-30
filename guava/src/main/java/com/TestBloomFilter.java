package com;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * 测试布隆过滤器
 * 布隆过滤器算法
 * 插入数据时，对数据调用多个hash算法，得到多个标识，
 * 然后将标识存放在位图中 bitMap
 * @author cck
 */
public class TestBloomFilter {

    public static void main(String[] args) {

        // 创建一个字符串布隆过滤器，字符集为UTF-8，预计插入100w条数据，错误率为0.001
        BloomFilter filter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), 1000000, 0.001F);

        // 填充数据
        filter.put("cck");

        // 查询数据
        System.out.println(filter.mightContain("cck"));
        System.out.println(filter.mightContain("cck2"));
    }

}
