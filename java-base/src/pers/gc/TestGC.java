package pers.gc;

/**
 * 测试GC标记阶段 --> 可达性分析算法中GC ROOTS的遍历
 * -Xmx400M -Xms400M -Xmn200M -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=75 -XX:+UseCMSInitiatingOccupancyOnly -XX:+PrintGCDetails
 * 堆总大小400M 初始大小400M 新生代大小200M 新生代使用ParNewGC 老年代使用CMSGC 内存达到75%是触发CMSGC 只使用指定的阀值（75）不自动调整 打印GC信息
 */
public class TestGC {

    private static final int _1MB = 1024 * 1024;
    private static final int LENGTH = 4000000;

    public static void main(String[] args) {

        Node head = null;
        // 构造一个链，GC 标记时就需要遍历这条链
        for (int i = 0; i <= LENGTH; i++) {
            Node node = new Node(i, head);
            head = node;
        }

        // 不加是4s钟 [Times: user=3.95 sys=0.11, real=1.42 secs]
        // 加了之后是 [Times: user=0.00 sys=0.00, real=0.00 secs]
        // head = null，GC标记时head就不是一个GC ROOTS，因此不需要去遍历这个对象，
        // 否则heap就是一个GC ROOTS，虚拟机需要遍历这个对象的属性。
        // 因此如果在编程过程中，head是不再使用的，可以赋值为null，加快标记阶段
//        head = null;  // help GC

        triggerGC();
    }

    /**
     * 触发GC
     */
    private static void triggerGC() {
        for (int i = 0; i < 200; i++) {
            byte[] bytes = new byte[_1MB];
        }
    }

    static class Node {
        private int val;
        private Node next;
        public Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }
    }

}
