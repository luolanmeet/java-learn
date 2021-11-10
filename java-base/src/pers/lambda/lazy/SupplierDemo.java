package pers.lambda.lazy;

import java.util.function.Supplier;

/**
 * @auther ken.ck
 * @date 2021/11/11 07:01
 */
public class SupplierDemo {

    public static void main(String[] args) {

        // 这一步的运算，不会立即执行
        // a的值，在使用的时候才计算，利用这一点，可以做一些性能上的优化改造。
        // 如实体的某些属性，是需要查询数据库的，在构造实体时可以不必立刻查出，
        // 在需要使用到时才触发执行数据库查询，其他耗性能的取值场景也一样。
        Supplier<Integer> a = () -> 10 + 1;
        int b = a.get() + 1;

        // 但每次使用都会计算，因此还需要对象封装

    }

}
