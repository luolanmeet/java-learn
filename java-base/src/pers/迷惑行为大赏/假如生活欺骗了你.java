package pers.迷惑行为大赏;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class 假如生活欺骗了你 {

    static class Happiness {
        @Override
        public String toString() {
            return "生活绝对是甜的";
        }
    }

    static class Sad {
        @Override
        public String toString() {
            return "悲伤的哭泣";
        }
    }

    public static void main(String[] args) throws Exception {

        List<Happiness> life = new ArrayList<>();
        life.add(new Happiness());

        something(life);

        for (Object o : life) {
            System.out.println(o);
        }

    }

    private static void something(List<Happiness> life) throws Exception {

        Method method = life.getClass().getDeclaredMethod("add", Object.class);
        method.invoke(life, new Sad());

        method.invoke(life, 555);
        method.invoke(life, "混入了奇怪的东西");
        method.invoke(life, false);
    }

}
