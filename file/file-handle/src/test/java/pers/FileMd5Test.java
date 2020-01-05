package pers;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class FileMd5Test {

    @Test
    public void getFileMd5() {
        System.out.println(FileMd5.getFileMd5(getFile("猫.rar")));
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