package pers.lambda.function.interface_;

import java.util.Calendar;
import java.util.function.Predicate;

/**
 * Predicate：谓词。可以传递返回boolean的函数，
 * 关注 test/or/and/negate 方法
 *
 * @author cck
 * @date 2020/11/1
 */
public class PredicateDemo {

    public static void main(String[] args) {

        String game = "lol大乱斗";

        // 想做的事情
        Predicate<String> p1 =  t -> t.startsWith("lol");
        Predicate<String> p2 =  t -> t.endsWith("大乱斗");

        // 21点才有空
        Predicate<Long> p3 =  t -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(t);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            return hour >= 1;
        };

        doXxx(p1, p2, game);
        doXxx(p1, p2, game + "观战");
        doXxx(p1, game,  p3, System.currentTimeMillis());
    }

    public static <T> void doXxx(Predicate<T> p, T t) {
        System.out.println(p.test(t) ? "GKD" : "Wait a minute");
    }

    public static <T> void doXxx(Predicate<T> p1, Predicate<T> p2, T t) {
        Predicate<T> p3 = p1.and(p2);
        doXxx(p3, t);
    }

    public static <T, R> void doXxx(Predicate<T> p1, T t, Predicate<R> p2, R r) {
        System.out.println(
                p1.test(t) && p2.test(r)
                        ? "do it"
                        : "no do it");
    }

}
