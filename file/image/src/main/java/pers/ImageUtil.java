package pers;

import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 借助Thumbnailator生成缩略图
 */
public class ImageUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageUtil.class);

    private final static Set<String> SUPPORT_IMG_TYPE = new HashSet<String>() {
        private static final long serialVersionUID = -7771976378726922433L;
        {
            add("jpg"); add("png"); add("gif"); add("jpeg"); add("webp");
        }
    };

    /**
     * 生成图片缩略图
     * 保存路径：与原图片路径相同
     * 缩略图名：t+原图名字
     * @param originalFile
     * @return
     */
    public static boolean generateImgThumbnail(File originalFile) {

        if (!isSupportImg(originalFile)) {
            return false;
        }

        try {
            Thumbnails.of(originalFile.getPath())
                    .scale(0.30f)
                    .toFile(FileUtil.getFileNewPath(originalFile, "t-" + originalFile.getName()));
        } catch (IOException e) {
            LOGGER.error("generate img thumbnail fail. error msg[{}]", e);
            return false;
        }

        return true;
    }

    /**
     * 返回文件是否是图片
     * @param file
     * @return
     */
    public static boolean isSupportImg(File file) {

        // 对象为空
        if (file == null) {
            LOGGER.info("file object is null");
            return false;
        }
        // 文件不存在
        if (!file.exists()) {
            LOGGER.info("file no exit. name[{}] path[{}]", file.getName(), file.getPath());
            return false;
        }
        // 文件夹
        if (file.isDirectory()) {
            LOGGER.info("file is directory, no support. name[{}] path[{}]", file.getName(), file.getPath());
            return false;
        }

        return isSupportImg(file.getName());
    }

    /**
     * 返回文件是否是图片
     * @param fileName
     * @return
     */
    public static boolean isSupportImg(String fileName) {

        // 是否支持
        Optional<String> fileType = FileUtil.getFileType(fileName);

        if (!fileType.isPresent()) {
            LOGGER.info("unknow file type, no an image. fileName[{}]", fileName);
            return false;
        }

        if (!SUPPORT_IMG_TYPE.contains(fileType.get())) {
            LOGGER.info("no support file type. fileName[{}] type[{}]", fileName, fileType.get());
            return false;
        }

        return true;
    }

}
