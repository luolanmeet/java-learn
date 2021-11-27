package pers.cpu.memory;

/**
 * @author cck
 * @date 2021/3/6 18:50
 */
public class Demo1 {

    public static void main(String[] args) {

        // int 4个字节，一个数组行1024，正好4kb
        // java二维数组，按照行优先存放
        // 用1024没测过效果
        int size = 10240;
        int[][] data = new int[size][size];

        long t1 = System.currentTimeMillis();

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                data[i][j] = 0;
        long t2 = System.currentTimeMillis();

        // 不符合 空间局部性 的写法
        for (int j = 0; j < size; j++)
            for (int i = 0; i < size; i++)
                data[i][j] = 0;

        long t3 = System.currentTimeMillis();

        System.out.println(t2 - t1 + "  " + (t3 - t2));
    }

}
