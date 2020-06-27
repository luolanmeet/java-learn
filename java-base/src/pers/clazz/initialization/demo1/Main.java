package pers.clazz.initialization.demo1;

public class Main {

    public static void main(String[] args) {
        // 通过子类引用父类的静态字段，不会导致子类初始化
        // 可通过-XX:+TraceClassLoading参数观察到类加载
        System.out.println(SubClass.value);
    }

}
