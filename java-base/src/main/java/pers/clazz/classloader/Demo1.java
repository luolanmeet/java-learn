package pers.clazz.classloader;

import java.io.IOException;
import java.io.InputStream;

/**
 * 比较两个类是否“相等”，只有这两个类是由同一个类加载器加载的前提下才有意义。
 */
public class Demo1 {

    public static void main(String[] args) throws Exception {

        ClassLoader classLoader = new ClassLoader() {

            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {

                try {

                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException();
                }
            }
        };

        Object obj = classLoader.loadClass("pers.clazz.classloader.Demo1").newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof pers.clazz.classloader.Demo1);
    }

}
