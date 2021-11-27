package pers.clazz.initialization.demo2;

public class Main {

    public static void main(String[] args) {
        // 通过数组定义来引用类，不会触发此类的初始化
        DemoClass[] demoClasses = new DemoClass[10];
    }

}
