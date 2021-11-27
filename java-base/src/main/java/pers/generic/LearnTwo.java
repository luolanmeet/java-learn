package pers.generic;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cck
 * @date 2021/2/3 21:30
 */
public class LearnTwo {

    static class Favorites {

        /**
         * 异构容器
         * 类型令牌 type token
         */
        private Map<Class<?>, Object> favorites = new HashMap<>();

        <T> void putFavorite(Class<T> type, T instance) {
            // put进去的时候，顺带检查了类型
            favorites.put(type, type.cast(instance));
//            favorites.put(type, instance);
        }

        <T> T getFavorite(Class<T> type) {
            return type.cast(favorites.get(type));
        }
    }

    public static void main(String[] args) {
        Favorites f = new Favorites();
        f.putFavorite(String.class, "Java");
        f.putFavorite(Integer.class, 0xcafebabe);
        f.putFavorite(Class.class, Favorites.class);

        String favoriteString = f.getFavorite(String.class);
        Integer favoriteInteger = f.getFavorite(Integer.class);
        Class favoriteClass = f.getFavorite(Class.class);

        System.out.printf("%s %x %s%n", favoriteString, favoriteInteger, favoriteClass.getSimpleName());
    }

}
