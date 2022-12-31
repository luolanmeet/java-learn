package pers.singleton;

/**
 * 饿汉式单例
 * 优点：没有使用任何的锁，性能较高
 * 缺点：类加载时便初始化，用或不用都会占用空间。大规模的这种单例会浪费很多空间
 */
public class HungrySingleton {

    private HungrySingleton() {}

    private static final HungrySingleton instance = new HungrySingleton();

    public static HungrySingleton getInstance() {
        return instance;
    }

}
