package pers.obj.two;

import org.apache.lucene.util.RamUsageEstimator;

public class ObjSizeDemo {

    static class User {
        private long id;
        private int age;
        private String name;
    }

    public static void main(String[] args) {

        /**
         * 64位虚拟机 + 指针压缩 的情况下输出32
         * 对象头 = 8 (mark work) + 4 (类型指针)
         * 实例数据 = 8 (long类型id字段) + 4 (int类型age字段) + 4(引用类型name字段)
         * 对齐填充 = 4
         */

        /**
         * 64位虚拟机 + 无指针压缩 (-XX:-UseCompressedOops) 的情况下输出40
         * 对象头 = 8 (mark work) + 8 (类型指针)
         * 实例数据 = 8 (long类型id字段)+ 4 (int类型age字段) + 8(引用类型name字段)
         * 对齐填充 = 4
         */
        System.out.println(RamUsageEstimator.shallowSizeOf(new User()));
        System.out.println(RamUsageEstimator.shallowSizeOfInstance(User.class));
    }

}
