package pers.jmm;

import static java.lang.System.out;

/**
 * cpu合并写
 * TODO 并没有看到4字节合并写的效果
 */
public class WriteCombining {

    private static final int ITERATIONS = Integer.MAX_VALUE;
    private static final int ITEMS = 1 << 24;
    private static final int MASK = ITEMS - 1;

    private static final byte[] arrayA = new byte[ITEMS];
    private static final byte[] arrayB = new byte[ITEMS];
    private static final byte[] arrayC = new byte[ITEMS];
    private static final byte[] arrayD = new byte[ITEMS];
    private static final byte[] arrayE = new byte[ITEMS];
    private static final byte[] arrayF = new byte[ITEMS];

    public static long runCaseOne() {
        long start = System.nanoTime();

        int i = ITERATIONS;
        while (--i != 0) {
            int slot = i & MASK;
            byte b = (byte)i;
            arrayA[slot] = b;
            arrayB[slot] = b;
            arrayC[slot] = b;
            arrayD[slot] = b;
            arrayE[slot] = b;
            arrayF[slot] = b;
        }

        return System.nanoTime() - start;
    }

    public static long runCaseTwo() {
        long start = System.nanoTime();

        int i = ITERATIONS;
        while (--i != 0) {
            int slot = i & MASK;
            byte b = (byte)i;
            arrayA[slot] = b;
            arrayB[slot] = b;
            arrayC[slot] = b;
        }

        i = ITERATIONS;
        while (--i != 0) {
            int slot = i & MASK;
            byte b = (byte)i;
            arrayD[slot] = b;
            arrayE[slot] = b;
            arrayF[slot] = b;
        }

        return System.nanoTime() - start;
    }

    public static void main(final String[] args) {
        for (int i = 1; i <= 3; i++) {
            out.println(i + " SingleLoop duration (ns) = " + runCaseOne());
            out.println(i + " SplitLoop  duration (ns) = " + runCaseTwo());
        }

        int result = arrayA[1] + arrayB[2] + arrayC[3] +
                arrayD[4] + arrayE[5] + arrayF[6];
        out.println("result = " + result);
    }

}
