package pers.singleton;

/**
 * 懒汉式单例
 * 优点：单例被使用时才进行初始化，不使用则不占用空间
 * 缺点：使用了锁来保证线程安全，性能会有所影响
 */
public class LazySingleton {

    private LazySingleton() {}

    private static volatile LazySingleton instance = null;

    /**
     * 双重检查锁
     * 使用了 volatile 修饰，避免JVM乱序执行导致的
     * instance实例返回，但实例的属性初始化未完成的问题
     */
    public static LazySingleton getInstance() {

        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }

        return instance;
    }
}
