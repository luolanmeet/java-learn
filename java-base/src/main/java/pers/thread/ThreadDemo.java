package pers.thread;

public class ThreadDemo {

    public void startThread1() {
        new Thread(() -> System.out.println("hi")).start();
    }

    public void startThread2() {
        // 和第一个方法是一样的，只是自己比较少这样写
        new Thread(this::method).start();
    }

    private void method() {
        System.out.println("hello");
    }

    public static void main(String[] args) throws InterruptedException {
        new ThreadDemo().startThread1();
        new ThreadDemo().startThread2();
        Thread.sleep(1000);
    }

}
