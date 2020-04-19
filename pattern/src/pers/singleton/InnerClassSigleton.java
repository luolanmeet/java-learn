package pers.singleton;

/**
 * 内部类单例
 * 外部类加载时并不会立即加载内部类，内部类不加载，则不会触发单例的初始化
 * 优点：延迟加载，不使用则不用空间；没加锁，不影响性能
 * 缺点：没法动态给单例传参数，多一个类文件 InnerClassSigleton$Holder.class
 */
public class InnerClassSigleton {

    private InnerClassSigleton() {
        // 防止反射创建实例
        if (Holder.instance != null) {
            throw new RuntimeException("multiple instances are not allowed");
        }
    }

    private Object readResolve(){
        // 防止序列化破坏单例，ObjectInputStream的readObject()最后会调到这个方法
        // 但序列化还是会创建新对象
        return Holder.instance;
    }

    public static InnerClassSigleton getInstance() {
        return Holder.instance; // 一个类的静态字段（非final）被访问时，会触发类的初始化
    }

    private static class Holder {
        private static InnerClassSigleton instance = new InnerClassSigleton();
    }

}
