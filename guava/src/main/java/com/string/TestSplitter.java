package com.string;

import java.util.List;
import java.util.Map;

import com.google.common.base.Splitter;

/**
 * 拆分字符串
 * 和 Joiner 对应
 * @author cck
 */
public class TestSplitter {
    
    public static void main(String[] args) {
        
        // 解析 url 上的参数挺好用的
        List<String> list = Splitter.on(";").splitToList("a;b;c");
        System.out.println(list);
        
        // 只解析为3个结果
        list = Splitter.on(";").limit(3).splitToList("a;b;c;d;e");
        System.out.println(list);
        
        // 只解析为3个结果
        list = Splitter.on(";").limit(3).splitToList("a;b");
        System.out.println(list);
        
        // 按照长度，切割字符串
        list = Splitter.fixedLength(3).splitToList("1234567");
        System.out.println(list);
        
        // trimResults() 去前后空格
        list = Splitter.on(";").trimResults().splitToList("  a;b  ; c ");
        System.out.println(list);
        
        // omitEmptyStrings() 去除结果为空格的内容
        list = Splitter.on(";").omitEmptyStrings().splitToList("a;;b;c;");
        System.out.println(list);
        
        // 转为map
        Map<String, String> map = Splitter.on("&").withKeyValueSeparator("=")
                .split("name=cck&password=12345");
        System.out.println(map);
    }
    
}
