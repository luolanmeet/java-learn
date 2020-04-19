package pers.singleton;

/**
 * 懒汉式单例
 * 优点：单例被使用时才进行初始化，不使用则不占用空间
 * 缺点：可读性较差
 */
public class LazySingleton {

    private LazySingleton() {}

    private static volatile LazySingleton instance = null;

    /**
     * 双重检查锁
     * 使用了 volatile 修饰，避免JVM指令重排导致的
     * 线程A创建了实例，但实例的属性还在初始化中，
     * 线程B判断到实例不位空，返回实例并且进行使用的问题。
     */
    public static LazySingleton getInstance() {

        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    // 1. 为 instance 分配空间
                    // 2. 调用 LazySingleton 的构造方法
                    // 3. 将 instance 对象指向分配的内存空间（之后instance就不为空了）
                    // 指令重排的原因，执行顺序可能为 1-2-3 或 1-3-2。加上 volatile ，禁止指令重排，保证顺序为1-2-3
                    instance = new LazySingleton();
                }
            }
        }

        return instance;
    }
}
