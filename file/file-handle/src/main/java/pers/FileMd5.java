package pers;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileMd5 {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileMd5.class);

    /**
     * 获取文件md5值
     * @param file
     * @return
     */
    public static String getFileMd5(File file) {

        // 对象为空
        if (file == null) {
            LOGGER.info("file object is null");
            return null;
        }

        // 文件不存在
        if (!file.exists()) {
            LOGGER.info("file no exit. name[{}] path[{}]", file.getName(), file.getPath());
            return null;
        }

        try {
            return DigestUtils.md5Hex(new FileInputStream(file));
        } catch (IOException e) {
            LOGGER.info("get file[{}] md5 fail. error msg [{}]", e);
            return null;
        }
    }
}
