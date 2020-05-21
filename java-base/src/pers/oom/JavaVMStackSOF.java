package pers.oom;

/**
 * HosSpot虚拟机中并不区分虚拟机栈和本地方法栈，
 * 因此-Xoss（设置本地方法栈大小）没有效果。
 * 栈容量只有-Xss参数设定。
 *
 * 如果线程请求的栈深度大于虚拟机所允许的最大深度，将抛出StackOverflowError。
 * 如果虚拟机栈内存允许动态扩展，当扩展到无法申请到足够内存时，将抛出OutOfMemoryError
 * HotSpot是选择不动态扩展的，虚拟机栈是不会有OOM的
 *
 * -Xss128k
 */
public class JavaVMStackSOF {

    int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {

        JavaVMStackSOF oom = new JavaVMStackSOF();

        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length：" + oom.stackLength);
            throw e;
        }
    }

}
