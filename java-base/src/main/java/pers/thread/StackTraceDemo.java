package pers.thread;

/**
 * 关于线程堆栈
 * @author cck
 * @date 2021/7/3 23:43
 */
public class StackTraceDemo {

    public void showCurrentMethod() {
        // 这个方法执行完成后，就不在堆栈中了
        method2();
        // 这个方法执行后，还是在堆栈中
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        StackTraceElement currentStackTrack = stackTrace[1];
        System.out.println("当前执行的方法全路径：" + currentStackTrack.getClassName() + "#" + currentStackTrack.getMethodName());
    }

    public void method() {
        showCurrentMethod();
    }

    public void method2() {
    }

    public static void main(String[] args) {
//        new StackTraceDemo().showCurrentMethod();
        new StackTraceDemo().method();
    }

}
