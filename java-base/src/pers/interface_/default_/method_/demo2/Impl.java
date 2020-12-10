package pers.interface_.default_.method_.demo2;

/**
 * @author cck
 * @date 2020/12/10 23:08
 */
public class Impl extends AbstractA implements B {
    @Override
    public void method() {
        // 调用接口A的默认方法
        super.method();
//        B.super.method();
    }
}
