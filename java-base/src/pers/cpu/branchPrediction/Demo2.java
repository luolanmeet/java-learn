package pers.cpu.branchPrediction;

/**
 * @author cck
 * @date 2021/4/8 23:41
 */
public class Demo2 {

    private static int A = 1;
    private final static long TIME = 50000000000L;
    public static void main(String[] args) {
        // switch 无法使用分支预测，如果绝大多数情况下是某种情况，
        // 应该单独使用if判断
        // TODO 未验证成功
        method1();
        method2();
    }

    private static void method1() {

        long sum = 0;
        long start = System.currentTimeMillis();
        for (long i = 0; i < TIME; i++) {
            if (A == 1) { // 提取出判断
                sum += 1;
            } else {
                switch (A) {
                    case 2:
                        sum += 2;
                        break;
                    case 3:
                        sum += 3;
                        break;
                    default:
                        break;
                }
            }
        }
        System.out.println("sum:" + sum + " use time:" + (System.currentTimeMillis() - start));
    }

    private static void method2() {

        long sum = 0;
        long start = System.currentTimeMillis();

        for (long i = 0; i < TIME; i++) {
            switch (A) {
                case 1:
                    sum += 1;
                    break;
                case 2:
                    sum += 2;
                    break;
                case 3:
                    sum += 3;
                    break;
                default:
                    break;
            }
        }
        System.out.println("sum:" + sum + " use time:" + (System.currentTimeMillis() - start));
    }

}
