package pers.oom;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 *
 * JDK8的HotSpot虚拟机使用元空间（Meta Space）实现方法区。
 *
 * 方法区是线程共享的内存区域，
 * 用于存储被虚拟机加载的【类型信息、常量、静态变量、即时编译器编译后的代码缓存】等数据。
 * 当无法满足新的内存分配时，将抛出OutOfMemoryError异常
 *
 * JDK8中，只要往方法区加载新类，即可使方法区OOM，即元空间空间不足
 * 元空间的JVM参数有两个：-XX:MetaspaceSize=N 和 -XX:MaxMetaspaceSize=N
 * 运行时调整元空间大小需要FullGC，因此两个配置适合设置为一样的值
 * -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=256m
 *
 * @auther ken.ck
 * @date 2021/11/27 22:49
 */
public class MetaSpaceOOM {

    public static void main(String[] args) throws Exception {

        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(MetaSpaceOOM.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object obj, Method method,
                                        Object[] args, MethodProxy proxy) throws Throwable {
                    return proxy.invokeSuper(obj, args);
                }
            });
            enhancer.create();
        }
    }

}
