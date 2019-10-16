package pers;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 递归获取某个路径下的所有文件的名字
 * 多线程版本，测试发现比单线程的更慢
 * 原因：
 *  1. 每个线程要执行的任务其实都不是耗时的操作，每次提交任务还需要去new一个Runnable
 *  2. 用于存储结果的集合是阻塞的集合，多线程没起多大作用
 */
public class FileNameUtilTwo {
    
    final static String SEG = "/";
    
    static ThreadPoolExecutor es;
    static {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        es = (ThreadPoolExecutor) Executors.newFixedThreadPool(availableProcessors);
    }
    
    public static void main(String[] args) throws Exception {
        
        long start = System.currentTimeMillis();
        String path = args[0]; // 文件路径
        String bashPath = args[1]; // 拼接的字符串
        String savePath = args[2]; // 获取的结果保存的路径
        
        // ArrayList是线程不安全的，封装后是个阻塞的集合 FIXME 找其他替代
        Collection<String> fileNames = new ArrayList<>();
        fileNames = Collections.synchronizedCollection(fileNames);
        
        getFileName(fileNames, path, bashPath);
    
        // FIXME 判断任务是否都完成替代方法
        while (es.getTaskCount() != es.getCompletedTaskCount()) {
        }
        
        es.shutdown();
        
        File f = new File(savePath + "/fileName.txt");
        f.createNewFile();
        
        try (FileWriter writer = new FileWriter(f)) {
            for (String fileName : fileNames) {
                writer.write(fileName);
                writer.write("\r\n");
            }
        }
        
        System.out.println("finish. use time : " + (System.currentTimeMillis() - start) + "ms");
    }
    
    private static void getFileName(Collection<String> fileNames, String currentPath, String basePath) {
        
        File file = new File(currentPath);
        
        if (!file.isDirectory()) {
            fileNames.add(basePath + SEG + file.getName());
            return ;
        }
        
        String tmp = basePath + SEG + file.getName();
        for (String childFileName : file.list()) {
            es.submit(() -> getFileName(fileNames, currentPath + SEG + childFileName, tmp));
        }
    }
    
}
