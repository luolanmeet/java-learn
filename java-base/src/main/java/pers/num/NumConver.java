package pers.num;

public class NumConver {

    public static void main(String[] args) {

        // 窄化类型转换

        // long 2 int 符号错误，只是简单地去掉除最低位的16个字节以外的内容
        // 1 10000000 00000000 00000000 00000000
        long l = 6442450944L;
        int i = (int) l;
        System.out.println(i);

        // double 2 int，转换后超过范围，则用int/float的最大值/最小值表示
        double d = 999999999999999999999996999999999999D;
        i = (int) d;
        System.out.println(i);

        float f = (float) d;
        System.out.println(f);
    }

}
