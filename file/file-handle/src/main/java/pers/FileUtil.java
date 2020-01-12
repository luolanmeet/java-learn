package pers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

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

    // 创建 16 * 16 * 16 * 16 = 65536个文件夹
    // private final static String STR = "0123456789ABCDEF";
    // 这里长度需要2的幂
    // 创建 8 * 8 * 8 * 8 = 4096‬个文件夹
    private final static String STR = "01234567";
    // 2的3次幂等于 STR_SIZE
    private final static int POWER = 3;
    private final static int STR_SIZE = STR.length();
    private final static int DOUBLE_POWER = POWER + POWER;
    private final static int SIZE_R_1 = STR_SIZE - 1;
    private final static int FD_SIZE_R_1 = STR_SIZE * STR_SIZE - 1;
    private final static int TOTAL_SIZE_R_1 = STR_SIZE * STR_SIZE * STR_SIZE * STR_SIZE - 1;
    public static void buildFileDirectory(String basePath) throws IOException {

        File file = new File(basePath);

        if (!file.exists()) {
            file.mkdirs();
        }

        File[] files = file.listFiles();
        if (files.length != 0) {
            return ;
        }

        // 创建两级目录，一级目录下64个文件夹， 一共64*64 4096‬个文件夹
        char[] cs = STR.toCharArray();
        for (char c1 : cs) {
            for (char c2 : cs) {
                for (char c3 : cs) {
                    for (char c4 : cs) {
                        String tmpPath = basePath + File.separator
                                + c1 + c2 + File.separator
                                + c3 + c4 + File.separator;
                        File tmpFile = new File(tmpPath);
                        if (!tmpFile.exists()) {
                            tmpFile.mkdirs();
                        }
                    }
                }
            }
        }
    }

    /**
     * 确定文件存在在那个文件夹中
     * @param str
     * @return
     */
    public static String getPath(String str) {

        // 通过文件名获取哈希值
        int hc = str.hashCode();
        // &总文件夹数（取模）
        int index = hc & TOTAL_SIZE_R_1;

        int f = index >> DOUBLE_POWER;
        int s = index & FD_SIZE_R_1;

        // 确定一级目录
        char f1 = STR.charAt(f >> POWER);
        char f2 = STR.charAt(f & SIZE_R_1);

        // 确定二级目录
        char s1 = STR.charAt(s >> POWER);
        char s2 = STR.charAt(s & SIZE_R_1);

        return new StringBuilder()
                .append(File.separator).append(f1).append(f2)
                .append(File.separator).append(s1).append(s2)
                .toString();
    }

    public static void main(String[] args) throws IOException {

        String basePath = "d://images";

        FileUtil.buildFileDirectory(basePath);

        for (int i = 0; i < 1000; i++){
            System.out.println(FileUtil.getPath(UUID.randomUUID().toString()));
        }

    }

}
