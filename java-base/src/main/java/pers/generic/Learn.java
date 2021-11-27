package pers.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cck
 * @date 2021/2/2 23:52
 */
public class Learn {

    /**
     * 使用无限制的类型参数
     */
    public static <E> void swapOne(List<E> list, int i, int j) {
        E e = list.get(i);
        list.set(i, e);
        list.set(j, e);
    }

    /**
     * 使用无限制的通配符
     */
    public static void swapTwo(List<?> list, int i, int j) {

        // 这样会无法编译，因为无法把null之外的任何值放到List<?>中
        // 但我们应该提供这样的接口出去
//        Object o = list.get(i);
//        list.set(i, list.get(j));
//        list.set(j, o);

        // 在内部封装另一个方法
        swapOne(list, i, j);
    }

    public static void main(String[] args) {

        // 在公共API中，第二种更好一些，因为更简单。
        // 如果类型参数只在方法声明中出现一次，就可以用通配符取代它

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        swapTwo(list, 0, 1);
        System.out.println(list);
    }

}
