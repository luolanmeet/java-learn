package pers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

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
        String savePath = System.getProperty("user.dir"); // 获取的结果保存的路径
        
        File f = new File(savePath + "/fileName.txt");
        f.createNewFile();
        
        // FileWriter&BufferedWriter是线程安全的
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            
            getFileName(bw, path, bashPath);
            
            // FIXME 判断任务是否都完成替代方法
            while (es.getTaskCount() != es.getCompletedTaskCount()) {
            }
            
            es.shutdown();
        }
        
        System.out.println("finish. use time : " + (System.currentTimeMillis() - start) + "ms");
    }
    
    private static void getFileName(BufferedWriter bw,
                                    String currentPath,
                                    String basePath) throws IOException {
        
        File file = new File(currentPath);
        
        if (!file.isDirectory()) {
            bw.write(basePath + SEG + file.getName() + " " + file.length());
            bw.write("\r\n");
            return ;
        }
        
        String tmp = basePath + SEG + file.getName();
        for (String childFileName : file.list()) {
            es.submit(() -> {
                try {
                    getFileName(bw, currentPath + SEG + childFileName, tmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
    
}
