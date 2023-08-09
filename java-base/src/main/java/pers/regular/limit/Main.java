package pers.regular.limit;

import java.util.regex.Pattern;


/**
 * @auther ken.ck
 * @date 2023/8/9 15:54
 */
public class Main {

    static String regex = "(\\w|_){1,64}@";
    static String string = "_________________________";

    public static void main(String[] args) {
        test0();
        test1();
        test2();
    }

    private static void test0() {
        long t = System.currentTimeMillis();
        try {
            Pattern compile = Pattern.compile(regex);
            compile.matcher(string).matches();
        } finally {
            System.out.println(System.currentTimeMillis() - t);
        }
    }

    private static void test1() {
        long t = System.currentTimeMillis();
        try {
            // 限制最多执行 100 ms
            Pattern compile = Pattern.compile(regex);
            compile.matcher(new TimedCharSequence(string, 100)).matches();
        } finally {
            System.out.println(System.currentTimeMillis() - t);
        }
    }

    private static void test2() {
        // 限制最多执行一百万次调用
        long t = System.currentTimeMillis();
        try {
            Pattern compile = Pattern.compile(regex);
            compile.matcher(new CountedCharSequence(string, 1_000_000)).matches();
        } finally {
            System.out.println(System.currentTimeMillis() - t);
        }
    }

}
