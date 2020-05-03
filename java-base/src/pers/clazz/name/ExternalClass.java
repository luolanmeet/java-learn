package pers.clazz.name;

import java.lang.reflect.Proxy;

/**
 * Java类类名规则
 */
public class ExternalClass {

    /**
     * 静态内部类
     * 类名：【外部类】$【静态内部类】 ，和外部类无太多关系。
     */
    public static class InnerStaticClass {}

    /**
     * 内部成员类
     * 类名：【外部类】$【内部成员类】
     */
    public class MemberClass {}
    public void memberClass() {
        System.out.println("内部成员类:" + MemberClass.class.getName());
    }

    /**
     * 局部内部类
     * 类名：【外部类】$【数字】【局部内部类】
     */
    public void localNestedClass() {
        class LocalNestedClass {}
        System.out.println("局部内部类:" + LocalNestedClass.class.getName());
    }

    /**
     * 匿名内部类（匿名类没有名字哈哈哈,匿名类不管是成员匿名类还是局部匿名类，都是一样的规则）
     * 类名：【外部类】$【数字】
     */
    public void anonymousClass() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
            }
        };
        System.out.println("匿名内部类:" + runnable.getClass().getName());
    }

    /**
     * lambda表达式类
     * 类名：【外部类】$$Lambda$【数字】
     */
    public void lambdaExpressionClass() {
        Runnable runnable = () -> {};
        System.out.println("lambda表达式类:" + runnable.getClass().getName());
    }

    /**
     * Java动态代理类
     * 类名：$Proxy【数字】
     */
    public void proxyClass() {
        Object obj = Proxy.newProxyInstance(ExternalClass.class.getClassLoader(), new Class[]{Runnable.class}, new ProxyClass());
        System.out.println("Java动态代理类:" + obj.getClass().getName());
    }

    public static void main(String[] args) {

        System.out.println("外部类:" + ExternalClass.class.getName());
        System.out.println("内部静态类:" + InnerStaticClass.class.getName());

        ExternalClass obj = new ExternalClass();
        obj.memberClass();
        obj.localNestedClass();
        obj.anonymousClass();
        obj.lambdaExpressionClass();
        obj.proxyClass();
    }

}
