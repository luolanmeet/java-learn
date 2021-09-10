package pers.monitor;

/**
 * @auther ken.ck
 * @date 2021/9/10 09:24
 */
public interface Listener {

    /**
     * File created
     *
     * @param rootPath file root path
     * @param fileName file name
     */
    void fileCreated(String rootPath, String fileName);

    /**
     * File modified
     *
     * @param rootPath file root path
     * @param fileName file name
     */
    void fileModified(String rootPath, String fileName);

    /**
     * File deleted
     *
     * @param rootPath file root path
     * @param fileName file name
     */
    void fileDeleted(String rootPath, String fileName);

}
