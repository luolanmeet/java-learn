package pers.syntax;

/**
 * 数值语法
 * @auther ken.ck
 * @date 2023/11/19 00:03
 */
public class NumSyntax {

    public static void main(String[] args) {
        shortMaxVal();
    }

    public static void shortMaxVal() {

        // short 最大值是 32767
        short s1 = 10000;
        // 这样会提示错误，int to short 需要强转
//        s1 = 32768;
        s1 = (short) 32768;

        // 这样会提示错误，int to short 需要强转
//        s1 = s1 + 1;
        s1 = (short) (s1 + 1);

        // 这样不会报错，也不用强转
        s1 += 1;
        System.out.println(s1);
    }


}
