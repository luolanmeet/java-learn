package pers.monitor;

/**
 * @auther ken.ck
 * @date 2021/9/10 09:29
 */
public abstract class Watcher extends Thread {

    protected long sleepTimeMills = 2000;

    protected String watchPath = null;

    protected String watchFileName = null;

    public abstract Watcher addListener(Listener... listener);

    public abstract void notifyListener();

    public Watcher watch(String root, String fName, long timeMills) {

        watchPath = root;
        watchFileName = fName;

        if (timeMills >= 0) {
            sleepTimeMills = timeMills;
        }

        return this;
    }
}
