package pers;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于扫描指定包下所有的类
 */
public class ClassScanUtil {

    private final static String SUFFIX_CLASS = ".class";

    public static Map<String, String> scan(String scanPackage) {

        // <类的全路径, 类名>
        Map<String, String> map = new HashMap<>();
        doScan(map, scanPackage);

        return map;
    }

    private static void doScan(Map<String, String> map, String scanPackage) {

        URL url = ClassScanUtil.class.getResource(
                "/" + scanPackage.replaceAll("\\.", "/"));

        File file = new File(url.getFile());
        if (!file.exists()) {
            return ;
        }

        // 是文件
        if (!file.isDirectory()) {
            if (file.getName().endsWith(SUFFIX_CLASS)) {
                String fileName = file.getName().replace(SUFFIX_CLASS, "");
                map.put(scanPackage + "." + fileName, fileName);
            }
            return ;
        }

        // 扫描文件夹
        for (File tmp : file.listFiles()) {

            if (tmp.isDirectory()) {
                doScan(map, scanPackage + "." + tmp.getName());
                continue;
            }

            if (tmp.getName().endsWith(SUFFIX_CLASS)) {
                String fileName = tmp.getName().replace(SUFFIX_CLASS, "");
                map.put(scanPackage + "." + fileName, fileName);
            }
        }
    }

    public static void main(String[] args) {
        Map<String, String> map = ClassScanUtil.scan("pers");
        System.out.println(map);
    }

}
