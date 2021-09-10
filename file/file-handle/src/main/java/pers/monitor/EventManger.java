package pers.monitor;

import pers.monitor.watcher.FileCreatedEventWatcher;
import pers.monitor.watcher.FileDeletedEventWatcher;
import pers.monitor.watcher.FileModifiedEventWatcher;

/**
 * @auther ken.ck
 * @date 2021/9/10 09:23
 */
public class EventManger {

    /**
     * watch file.
     *
     * @param event     file event
     * @param fileRoot  file root path
     * @param fileName  file name
     * @param listeners
     * @see Event
     * @see Listener
     */
    public static void watch(Event event, String fileRoot, String fileName, Listener... listeners) {
        watch(event, fileRoot, fileName, -1, listeners);
    }

    /**
     * watch file.
     *
     * @param event     file event
     * @param fileRoot  file root path
     * @param fileName  file name
     * @param timeMills
     * @param listeners
     * @see Event
     * @see Listener
     */
    public static void watch(Event event, String fileRoot, String fileName, long timeMills, Listener... listeners) {

        Watcher watcher = null;

        switch (event) {
            case FILE_MODIFIED_EVENT:
                watcher = new FileModifiedEventWatcher();
                watcher.watch(fileRoot, fileName, timeMills)
                        .addListener(listeners)
                        .start();
                break;
            case FILE_DELETED_EVENT:
                watcher = new FileDeletedEventWatcher();
                watcher.watch(fileRoot, fileName, timeMills)
                        .addListener(listeners)
                        .start();
                break;
            case FILE_CREATED_EVENT:
                watcher = new FileCreatedEventWatcher();
                watcher.watch(fileRoot, fileName, timeMills)
                        .addListener(listeners)
                        .start();
                break;
            case ANY_ENVET:
                //same as default
            default:
                Watcher modifyWatcher = new FileModifiedEventWatcher();
                Watcher deleteWatcher = new FileDeletedEventWatcher();
                Watcher createWatcher = new FileCreatedEventWatcher();

                modifyWatcher.watch(fileRoot, fileName, timeMills)
                        .addListener(listeners)
                        .start();

                deleteWatcher.watch(fileRoot, fileName, timeMills)
                        .addListener(listeners)
                        .start();

                createWatcher.watch(fileRoot, fileName, timeMills)
                        .addListener(listeners)
                        .start();
        }
    }

}
