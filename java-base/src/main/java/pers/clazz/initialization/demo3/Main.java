package pers.clazz.initialization.demo3;

public class Main {

    public static void main(String[] args) {
        // 常量在编译阶段会存入调用类的常量池中，也就是Main类中，本质上没有直接引用到
        // 定义常量的类，也就是ConstClass类，因此也不会触发定义常量的类的初始化。
        System.out.println(ConstClass.HELLO_WORLD);
    }

}
