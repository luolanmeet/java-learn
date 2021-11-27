package pers.gc;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class TestGCBase {

    public static void main(String[] args) {

        // 获取当前使用的垃圾收集器
        List<GarbageCollectorMXBean> gcMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcMXBean : gcMXBeans) {
            System.out.println(gcMXBean.getName());
            System.gc();
        }
    }

}