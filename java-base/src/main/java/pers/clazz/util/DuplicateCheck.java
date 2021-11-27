package pers.clazz.util;

import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * @auther ken.ck
 * @date 2021/8/19 08:30
 */
public class DuplicateCheck {

    private DuplicateCheck() {}

    public static void checkDuplicate(Class cls) {
        checkDuplicate(cls.getName().replace('.', '/') + ".class");
    }

    public static void checkDuplicate(String path) {
        try {
            // 在ClassPath搜文件
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(path);
            Set<String> files = new HashSet<String>();
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String file = url.getFile();
                    if (file != null && file.length() > 0) {
                        files.add(file);
                    }
                }
            }
            // 如果有多个，就表示重复
            System.err.println("Duplicate class " + path + " in " + files.size() + " jar " + files);
        } catch (Throwable e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DuplicateCheck.checkDuplicate(String.class);
//        DuplicateCheck.checkDuplicate(com.alibaba.fastjson.JSONObject.class);
    }

}
