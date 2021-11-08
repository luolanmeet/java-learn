package com.pers.util;

/**
 * 范围类
 * @auther ken.ck
 * @date 2021/11/7 21:12
 */
public class Range <T extends Comparable> {

    private T left;
    private T right;

    public static <T extends Comparable>Range range(T left, T right) {
        return new Range(left, right);
    }

    private Range(T left, T right) {
        this.left = left;
        this.right = right;
    }

    public boolean isContain(T t) {
        return left.compareTo(t) != 1 && right.compareTo(t) != -1;
    }

}
