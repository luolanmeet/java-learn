package pers.clazz;

import org.openjdk.jol.info.ClassLayout;

public class ObjectLayout {

    /**
         java.lang.Object object internals:
         OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
         0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
         4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         8     4        (object header)                           e5 01 00 20 (11100101 00000001 00000000 00100000) (536871397)
         12     4        (loss due to the next object alignment)
         Instance size: 16 bytes
         Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

         对象的内部布局分3部分，
            对象头
            实例数据
            填充数据 padding
         对象头：
            1~8个字节：markwork。固定为8字节。
            9~12个字节：class point 类型指针，指向对象的类类型。开启指针压缩时为4字节，不开启为8字节（-XX:+UseCompressedOops）
         填充数据 padding：
            64位的虚拟机以8字节对齐，数据大小不能被8整除则进行填充。
            32位的虚拟机以4字节对齐。
     */

    /**
     * 使用jol查看对象内部布局
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {

        // jvm 默认开启偏向锁（-XX:+UseBiasedLocking）
        // 在jvm启动后4s开启（-XX:BiasedLockingStartupDelay=4000），会看到101
//        TimeUnit.SECONDS.sleep(5);

        Object obj = new Object();
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        // 加锁之后可以看到对象头的数据发生变化。
        // 锁标识发生改变
        synchronized (obj) {
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        }
    }

}
