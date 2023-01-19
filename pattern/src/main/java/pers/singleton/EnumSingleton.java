package pers.singleton;

/**
 * 枚举式单例
 * 枚举式只是保证了其值是单例的，在以下例子对应的是 INSTANCE 是单例的
 * 优点：天然防止反射破环单例,Constructor#newInstance执行的时候就会判断是否是枚举，优雅
 * 缺点：也是饿汉式的，不适合有大量单例对象的场景
 */
public enum EnumSingleton {

    INSTANCE;

    public String getStr() {
        return "hello";
    }

    private static EnumSingleton getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        EnumSingleton instance = EnumSingleton.getInstance();
        System.out.println(instance.getStr());
    }

}
