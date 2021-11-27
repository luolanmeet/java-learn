package pers.oom;

import java.io.IOException;
import java.io.InputStream;

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
            ClassLoader classLoader = new ClassLoader() {
                @Override
                public Class<?> loadClass(String name) throws ClassNotFoundException {
                    try {
                        String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                        InputStream is = getClass().getResourceAsStream(fileName);
                        if (is == null) {
                            return super.loadClass(name);
                        }
                        byte[] b = new byte[is.available()];
                        is.read(b);
                        return defineClass(name, b, 0, b.length);
                    } catch (IOException e) {
                        throw new ClassNotFoundException();
                    }
                }
            };
            Class<?> clazz = classLoader.loadClass("pers.oom.MetaSpaceOOM");
            System.out.println(clazz);
        }

    }

}
