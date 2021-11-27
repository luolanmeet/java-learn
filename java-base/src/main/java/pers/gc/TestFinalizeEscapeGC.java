package pers.gc;

/**
 * 对象可以在被GC时自我拯救
 * 这种自救的机会只有一次，因为finalize()方法最多被系统调用一次
 */
public class TestFinalizeEscapeGC {

    public static TestFinalizeEscapeGC SAVE_HOOK = null;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed!");
        SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new TestFinalizeEscapeGC();

        // 对象第一次拯救自己
        SAVE_HOOK = null;
        System.gc();
        // finalize方法优先级很低，暂停等执行
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            System.out.println("i am still alive");
        } else {
            System.out.println("i am dead");
        }

        // 自救失败
        SAVE_HOOK = null;
        System.gc();
        Thread.sleep(500);
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            System.out.println("i am still alive");
        } else {
            System.out.println("i am dead");
        }
    }

}
