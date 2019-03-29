package per.cglib.filter;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 * 
 * @author cck
 */
public class Main {

    public static void main(String[] args) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(RealSubject.class);

        ProxyOne proxyOne = new ProxyOne();
        ProxyTwo proxyTwo = new ProxyTwo();

        // 第三个proxy是空的CallBack,不对任何方法进行拦截
        enhancer.setCallbacks(new Callback[] {proxyOne, proxyTwo, NoOp.INSTANCE});

        // 设置拦截策略，对某个方法使用指定拦截策略
        ProxyFilter filter = new ProxyFilter();
        enhancer.setCallbackFilter(filter);

        RealSubject realSubject = (RealSubject) enhancer.create();
        realSubject.sayHello("cck");
        realSubject.sayHelloWorld("cck");
        realSubject.sayHi("cck");
    }

}
