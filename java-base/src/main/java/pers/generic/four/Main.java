package pers.generic.four;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @auther ken.ck
 * @date 2023/7/5 20:46
 */
public class Main {

    public static void main(String[] args) {

        Type type0 = Wrapper.getGenericRuntimeType(new Wrapper<List<String>>());
        Type type1 = Wrapper.getGenericRuntimeType(new Wrapper<String>() {});
        Type type2 = Wrapper.getGenericRuntimeType(new Wrapper<List<String>>() {});

        // null
        System.out.println(type0);
        // class java.lang.String
        System.out.println(type1);
        // java.util.List<java.lang.String>
        System.out.println(type2);
    }

}
