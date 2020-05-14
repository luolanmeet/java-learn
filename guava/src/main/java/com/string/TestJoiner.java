package com.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;

/**
 * 连接字符串
 * 和 Splitter 对应
 * @author cck
 */
public class TestJoiner {
    
    public static void main(String[] args) {
        
        // 拼接字符串
        List<String> list = new ArrayList<>();
        list.add("A");list.add("B");list.add("C");
        String str = Joiner.on(";").join(list);
        System.out.println(str);

        // 拼接在已有的字符串上
        StringBuilder strbuil = new StringBuilder("Z;");
        Joiner.on(";").appendTo(strbuil, list);
        System.out.println(strbuil.toString());

        // useForNull("str") 用str替换null
        list = Arrays.asList("1", "2", null, "3");  // 有 null 时，调用 Joinder会报错
        str = Joiner.on(";").useForNull("0").join(list);
        System.out.println(str);
        
        // skipNulls() 忽略null
        list = Arrays.asList("1", "2", null, "3");
        str = Joiner.on(";").skipNulls().join(list);
        System.out.println(str);
        
        //　将一个map组装成一个字符串，构建 url路径上的参数时会很好用
        Map<String, String> map = new HashMap<>();
        map.put("name", "cck");map.put("password", "123456");
        str = Joiner.on("&").withKeyValueSeparator("=").join(map);
        System.out.println(str);
    }
    
}
