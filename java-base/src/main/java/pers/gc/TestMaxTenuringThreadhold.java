package pers.gc;

import java.util.concurrent.TimeUnit;

/**
 * 测试 -XX:MaxTenuringThreshold
 * -Xmx300M -Xms300M -Xmn100M -XX:NewSize=100M -XX:MaxTenuringThreshold=2 -XX:+UseSerialGC -XX:+PrintGCDetails
 *
 * -XX:MaxTenuringThreshold=2 应该是经过两次GC就进入了老年代
 *
 * [GC (System.gc()) [PSYoungGen: 10240K->4792K(89600K)] 10240K->4800K(294400K), 0.0028312 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * [Full GC (System.gc()) [PSYoungGen: 4792K->0K(89600K)] [ParOldGen: 8K->4731K(204800K)] 4800K->4731K(294400K), [Metaspace: 3475K->3475K(1056768K)], 0.0050338 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * 第二次是Full GC,在这一次GC中 [PSYoungGen: 4792K->0K(89600K)] 新生代的4792KB变成了0KB
 * [ParOldGen: 8K->4731K(204800K)] 老年代的8K变成了4731KB，说明新生的对象晋升到老年代了
 *
 * 可改称其他值进行测试
 */
public class TestMaxTenuringThreadhold {

    public static void main(String[] args) throws InterruptedException {

        // 测试时要主要避免大对象直接进入老年代
        Data data = new Data();

        for (int i = 0; i < 10; i++) {
            System.gc();
            TimeUnit.SECONDS.sleep(3);
            System.out.println();
        }

    }

    static class Data {
        static final int _1MB = 1024 * 1024;
        byte[] bytes = new byte[4 * _1MB];
    }

}
