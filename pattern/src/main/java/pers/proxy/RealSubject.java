package pers.proxy;

public class RealSubject implements Subject {

    @Override
    public void sayHello(String name) {
        System.out.println(name + " say hello");
    }
}
