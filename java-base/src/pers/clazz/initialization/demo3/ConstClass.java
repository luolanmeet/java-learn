package pers.clazz.initialization.demo3;

public class ConstClass {

    static {
        System.out.println("ConstClass init!");
    }

    static final String HELLO_WORLD = "hello world";

}
