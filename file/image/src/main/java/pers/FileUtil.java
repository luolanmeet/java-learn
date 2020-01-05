package pers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Optional;

public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 获取文件的路径
     * @param file
     * @param newName
     * @return
     */
    public static String getFileNewPath(File file, String newName) {
        return file.getParent() + File.separator + newName;
    }

    /**
     * 获取文件类型
     * @param fileName
     * @return
     */
    public static Optional<String> getFileType(String fileName) {
        String[] strs = fileName.split("\\.");

        // 文件名字至少是 xxx.xxx
        if (strs.length < 2) {
            LOGGER.info("unknow file type. fileName[{}]", fileName);
            return Optional.empty();
        }

        return Optional.of(strs[strs.length - 1]);
    }

}
