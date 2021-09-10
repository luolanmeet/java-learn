package pers.monitor.watcher;

import pers.monitor.Listener;
import pers.monitor.Watcher;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @auther ken.ck
 * @date 2021/9/10 09:34
 */
public class FileModifiedEventWatcher extends Watcher {

    private List<Listener> modifyEventListeners = new ArrayList<Listener>();

    @Override
    public void run() {
        String path = watchPath + "/" + watchFileName;
        File file = new File(path);

        long lastModified = Long.MAX_VALUE;

        boolean fileExits = file.exists();

        if (fileExits) {
            lastModified = file.lastModified();
        }

        while (true) {
            long modified = file.lastModified();
            if (fileExits && modified > lastModified) {
                notifyListener();
            }
            lastModified = modified;

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
            modifyEventListeners.add(listener);
        }

        return this;
    }

    @Override
    public void notifyListener() {
        Iterator<Listener> iterator = modifyEventListeners.iterator();
        while (iterator.hasNext()) {
            Listener listener = iterator.next();
            listener.fileModified(watchPath, watchFileName);
        }
    }

}
