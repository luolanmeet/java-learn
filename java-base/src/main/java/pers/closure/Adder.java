package pers.closure;

import java.util.function.Function;

/**
 * 闭包
 * Java里边，想成对象会比较好理解
 * @auther ken.ck
 * @date 2021/12/19 17:17
 */
public class Adder {

    public static Function<Integer, Integer> getAdderFunc() {
        Holder sum = new Holder(0);
        return value -> {
            sum.value += value;
            return sum.value;
        };
    }

    public static void main(String[] args) {

        Function<Integer, Integer> adderFunc = getAdderFunc();
        for (int i = 0; i < 10; i++) {
            System.out.printf("0 + ... + %d = %d\n", i, adderFunc.apply((i)));
        }
    }
}
