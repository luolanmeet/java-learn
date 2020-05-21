package pers.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆中存储Java对象，只需不断创建对象，并且保证GC Roots到对象之间有可达路径
 * 避免被回收即可产生内存溢出异常。
 *
 * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 */
public class HeadOOM {

    public static void main(String[] args) {

        List<HeadOOM> list = new ArrayList<>();
        while (true) {
            list.add(new HeadOOM());
        }

    }

}
