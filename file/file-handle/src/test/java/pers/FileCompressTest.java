package pers;

import org.junit.Test;

import java.io.File;

public class FileCompressTest {

    @Test
    public void compress() {
    }

    @Test
    public void decompress() {
        FileCompress.decompress(getFile("海贼王.zip"));
    }

    /**
     * 获取/test/resource下的文件
     * @param fileName
     * @return
     */
    public static File getFile(String fileName) {
        String path = System.getProperty("user.dir") + "/src/test/java/resources/" + fileName;
        return new File(path);
    }

}