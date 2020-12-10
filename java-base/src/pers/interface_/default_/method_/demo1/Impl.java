package pers.interface_.default_.method_.demo1;

/**
 * @author cck
 * @date 2020/12/10 23:03
 */
public class Impl implements A, B {
    @Override
    public void method() {
        A.super.method();
    }
}
