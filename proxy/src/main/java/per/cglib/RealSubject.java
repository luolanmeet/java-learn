package per.cglib;

/**
 * CGLIB 是通过继承的方式进行代理，
 * 如果类声明为final，那将无法使用CGLIB进行动态代理
 * final类无法被继承
 *
 * 如果方法声明为final，将无法被CGLIB生成的子类进行代理。
 * final方法无法被子类继承
 */
public class RealSubject {

    public void sayHello(String name) {
        System.out.println(name + " say hello");
    }
    
    public final void sayHi(String name) {
        System.out.println(name + " say hi");
    }
    
}
