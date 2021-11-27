package pers.obj.one;

/**
 * 计算对象大小
 * https://blog.csdn.net/yunqiinsight/article/details/80431831
 */
public class ObjSizeDemo {

    static class User {
        private int age;
        private String name;
        private String name2;
    }

    public static void main(String[] args) {

        /**
         * 64位虚拟机 + 指针压缩 的情况下输出24
         * 对象头 = 8 (mark work) + 4 (类型指针)
         * 实例数据 = 4 (int类型age字段) + 4(引用类型name字段)
         * 对齐填充 = 4
         */

        /**
         * 64位虚拟机 + 无指针压缩 (-XX:-UseCompressedOops) 的情况下输出32
         * 对象头 = 8 (mark work) + 8 (类型指针)
         * 实例数据 = 4 (int类型age字段) + 8(引用类型name字段)
         * 对齐填充 = 4
         */
        System.out.println(ObjSizeTool.getObjectSize(new User()));

    }

}
