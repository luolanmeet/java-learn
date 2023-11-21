package pers.syntax;

import java.util.Arrays;

/**
 * 数组语法
 * @auther ken.ck
 * @date 2023/11/18 23:53
 */
public class ArraySyntax {

    public static void main(String[] args) {
        multidimensional();
        multidimensional2();
    }

    public static void multidimensional() {

        int[][] a = new int[2][1];
        int[] b = new int[3];
        b[0] = 1; b[1] = 2; b[2] = 3;
        // a[0]指向了b，而不是b数组的值赋予了a[0]
        a[0] = b;
        System.out.println(Arrays.toString(a[0]));
        System.out.println(a[0][0]);
        System.out.println(a[0][1]);

        // 越界
        try {
            System.out.println(a[1][1]);
        } catch (Exception e) {
            System.out.println("a[1][1] " + e.getMessage());
        }
    }

    public static void multidimensional2() {

        int[][][] a = new int[2][1][2];
        int[] b = new int[3];
        // 无法赋值， 无法给[][]类型赋予[]值
//        a[0] = b;
        // 这个是可以的
        a[0][0] = b;
    }

}
