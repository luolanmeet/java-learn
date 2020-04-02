package pers.迷惑行为大赏;

import java.lang.reflect.Field;

public class 每日一遍 {

    public static void main(String[] args) throws Exception {

        something();

        Integer time = 1;
        for (int i = 0; i < time; i++) {
            System.out.println("投币点赞收藏");
        }

    }

    private static void something() throws Exception {
        Field field = Integer.class.getDeclaredField("value");
        field.setAccessible(true);
        Integer a = 1;
        field.set(a, 1_000_000_000);
    }

}
