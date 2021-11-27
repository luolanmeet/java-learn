package pers.cpu.branchPrediction;

import java.util.Arrays;
import java.util.Random;

/**
 * @author cck
 * @date 2021/3/6 18:33
 */
public class Demo1 {

    public static void main(String[] args) {

        int[] array = getArray();
        getSum(array, false);
        getSum(array, false);
        getSum(array, false);
        getSum(array, false);
        getSum(array, true);
    }

    public static void getSum(int[] datas, boolean isSort) {

        long sum = 0;
        long start = System.currentTimeMillis();

        if (isSort)
            Arrays.sort(datas);

        for (int i = 0; i < 10000; i++) {
            for (int data : datas) {
                // 这有个判断的分支，
                // 如果数据有序，历史执行记录中此判断是有规律的，CPU更容易预测到分支
                if (data >= 128) {
                    sum += data;
                }
            }
        }
        System.out.println("sum:" + sum + " use time:" + (System.currentTimeMillis() - start));
    }

    public static int[] getArray() {
        int size = 30000;
        int[] datas = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            datas[i] = random.nextInt() & 255;
        }
        return datas;
    }

}
