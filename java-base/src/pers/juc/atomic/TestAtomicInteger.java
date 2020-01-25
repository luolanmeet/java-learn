package pers.juc.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class TestAtomicInteger {

    public static void main(String[] args) {

        AtomicInteger atomicInteger = new AtomicInteger(3);
        System.out.println(atomicInteger.get());

        // a是原来的值，b是传入的值（就是11）,函数的返回值就是最终的值
        atomicInteger.accumulateAndGet(11, (a, b) -> a > b ? a : b);
        System.out.println(atomicInteger.get());

        // x就是原来的值，函数的返回值就是最终更新的值
        atomicInteger.updateAndGet((x) -> x + 2);
        System.out.println(atomicInteger.get());

    }

}
