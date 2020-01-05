package pers.singleton;

/**
 * 枚举式单例
 * 枚举式只是保证了其值是单例的，在以下例子对应的是 INSTANCE 是单例的
 */
public class EnumSingleton {

    private EnumSingleton() {}

    private static EnumSingleton getInstance() {
        return Singleton.INSTANCE.enumSingleton;
    }

    private enum Singleton {

        INSTANCE;

        private EnumSingleton enumSingleton;
        Singleton() {
            System.out.println("enum constructor");
            enumSingleton = new EnumSingleton();
        }
    }

    public static void main(String[] args) {

        System.out.println(EnumSingleton.getInstance());
        System.out.println(EnumSingleton.getInstance());
    }

}
