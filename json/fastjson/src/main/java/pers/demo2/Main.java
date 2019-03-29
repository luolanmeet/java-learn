package pers.demo2;

import java.util.Arrays;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

public class Main {

    /**
     * 剔除指定字段
     * @param obj
     * @param fields
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T executeField(Object obj, String ... fields) {

        SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter();
        simplePropertyPreFilter.getExcludes().addAll(Arrays.asList(fields));

        return (T) JSON.parseObject(JSON.toJSONString(obj, simplePropertyPreFilter), obj.getClass());
    }
    
    public static void main(String[] args) {
       
        ClassB b1 = new ClassB(1, "1");
        ClassB b2 = new ClassB(2, "2");
        
        ClassA a = new ClassA();
        a.setName("a");
        a.setCbs(Arrays.asList(b1, b2));
        
        String str = JSON.toJSONString(a);
        System.out.println(str);

        ClassA ca = JSON.parseObject(str, ClassA.class);
        System.out.println(ca);

        ca = executeField(ca, "name");
        System.out.println(ca);
    }
    
}
