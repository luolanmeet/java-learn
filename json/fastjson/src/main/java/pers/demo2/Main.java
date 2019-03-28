package pers.demo2;

import java.util.Arrays;

import com.alibaba.fastjson.JSON;

public class Main {
    
//    public static Object executeField(Object obj, String ... fields) {
//    }
    
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
        
    }
    
}
