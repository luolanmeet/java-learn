package pers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

        File f = new File(savePath + "/fileName.txt");
        f.createNewFile();
        
        try (FileWriter writer = new FileWriter(f);
             BufferedWriter bw = new BufferedWriter(writer)) {
            getFileName(bw, path, bashPath);
        }
        
        System.out.println("finish. use time : " + (System.currentTimeMillis() - start) + "ms");
    }
    
    private static void getFileName(BufferedWriter bw,
                                    String currentPath,
                                    String basePath) throws IOException {
    
        File file = new File(currentPath);
        
        if (!file.isDirectory()) {
            bw.write(basePath + SEG + file.getName());
            bw.write("\r\n");
            return ;
        }
    
        String tmp = basePath + SEG + file.getName();
        for (String childFileName : file.list()) {
            getFileName(bw, currentPath + SEG + childFileName, tmp);
        }
    }
    
}
