package pers.monitor.watcher;

import pers.monitor.Listener;
import pers.monitor.Watcher;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @auther ken.ck
 * @date 2021/9/10 09:32
 */
public class FileCreatedEventWatcher extends Watcher {

    private List<Listener> createEventListeners = new ArrayList<Listener>();

    @Override
    public void run() {
        String path = watchPath + "/" + watchFileName;
        File file = new File(path);

        boolean fileExits = file.exists();

        while (true) {

            if (!fileExits && file.exists()) {
                notifyListener();
            }

            fileExits = file.exists();

            try {
                Thread.sleep(sleepTimeMills);
            } catch (InterruptedException e) {
                //do noting
            }
        }
    }

    @Override
    public Watcher addListener(Listener... listeners) {

        for (Listener listener : listeners) {
            createEventListeners.add(listener);
        }
        return this;
    }

    @Override
    public void notifyListener() {
        Iterator<Listener> iterator = createEventListeners.iterator();
        while (iterator.hasNext()) {
            Listener listener = iterator.next();
            listener.fileCreated(watchPath, watchFileName);
        }
    }

}

