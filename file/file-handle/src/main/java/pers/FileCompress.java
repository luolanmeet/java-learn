package pers;

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

        unzip(compressFile, compressFile.getParent());
    }

    private static void unzip(File compressFile, String savePath) {

        try (ZipArchiveInputStream zipArchiveInputStream = new ZipArchiveInputStream(new FileInputStream(compressFile),"gbk")) {
            ZipArchiveEntry entry = null;
            while ((entry = zipArchiveInputStream.getNextZipEntry()) != null) {
                System.out.println(entry.getName());
                try (OutputStream os = new BufferedOutputStream(new FileOutputStream(new File(savePath, entry.getName())))) {
                    IOUtils.copy(zipArchiveInputStream, os);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
