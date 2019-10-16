package pers;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 递归获取某个路径下的所有文件的名字
 * 之前是为了看fastDFS里边存哪些文件而写的
 */
public class FileNameUtil {
    
    final static String SEG = "/";
    
    public static void main(String[] args) throws Exception {
        
        long start = System.currentTimeMillis();
        String path = args[0]; // 文件路径
        String bashPath = args[1]; // 拼接的字符串
        String savePath = args[2]; // 获取的结果保存的路径
        
        List<String> fileNames = new ArrayList<>();
        getFileName(fileNames, path, bashPath);

        File f = new File(savePath + "/fileName.txt");
        f.createNewFile();
        
        try (FileWriter writer = new FileWriter(f);) {
            for (String fileName : fileNames) {
                writer.write(fileName);
                writer.write("\r\n");
            }
        }
        
        System.out.println("finish. use time : " + (System.currentTimeMillis() - start) + "ms");
    }
    
    private static void getFileName(List<String> fileNames, String currentPath, String basePath) {
    
        File file = new File(currentPath);
        
        if (!file.isDirectory()) {
            fileNames.add(basePath + SEG + file.getName());
            return ;
        }
    
        String tmp = basePath + SEG + file.getName();
        for (String childFileName : file.list()) {
            getFileName(fileNames, currentPath + SEG + childFileName, tmp);
        }
    }
    
}
