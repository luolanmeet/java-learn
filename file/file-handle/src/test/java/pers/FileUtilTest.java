package pers;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class FileUtilTest {

    @Test
    public void buildFileDirectory() throws Exception {

        // 测试多线程创建文件夹
        CountDownLatch count = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    count.await();
                    FileUtil.buildFileDirectory("d://images");
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }).start();
            count.countDown();
        }

        System.in.read();
    }

}
