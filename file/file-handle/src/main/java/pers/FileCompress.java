package pers;

import com.github.junrar.Junrar;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 文件压缩，解压等
 */
public class FileCompress {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileCompress.class);

    /**
     * 将文件解压到和原文件的同级的目录
     * @param compressFile
     */
    public static void decompress(File compressFile) {

        // 对象为空
        if (compressFile == null) {
            LOGGER.info("file object is null");
            return ;
        }
        // 文件不存在
        if (!compressFile.exists()) {
            LOGGER.info("file no exit. name[{}] path[{}]", compressFile.getName(), compressFile.getPath());
            return ;
        }

        String fileName = compressFile.getName().toLowerCase();
        if (fileName.endsWith(".zip")) {
            unzip(compressFile, compressFile.getParent());
        } else if (fileName.endsWith(".rar")) {
            unrar(compressFile, compressFile.getParent());
        }
    }

    /**
     * 解压rar文件
     * @param compressFile
     * @param savePath
     * @return
     */
    private static boolean unrar(File compressFile, String savePath) {

        try {
            Junrar.extract(compressFile, new File(savePath));
        } catch (Exception e) {
            LOGGER.info("unrar file[{}] fail. error msg [{}]", e);
            return false;
        }

        return true;
    }

    /**
     * 解压zip文件
     * @param compressFile
     * @param savePath
     */
    private static boolean unzip(File compressFile, String savePath) {

        try (ZipArchiveInputStream zipArchiveInputStream = new ZipArchiveInputStream(new FileInputStream(compressFile),"gbk")) {
            ZipArchiveEntry entry = null;
            while ((entry = zipArchiveInputStream.getNextZipEntry()) != null) {
                try (OutputStream os = new BufferedOutputStream(new FileOutputStream(new File(savePath, entry.getName())))) {
                    IOUtils.copy(zipArchiveInputStream, os);
                }
            }
        } catch (IOException e) {
            LOGGER.info("unzip file[{}] fail. error msg [{}]", e);
            return false;
        }

        return true;
    }

}
