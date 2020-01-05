package pers;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class ImageUtilTest {

    @Test
    public void getImgThumbnail() {
        ImageUtil.generateImgThumbnail(getFile("空洞骑士.jpg"));
        ImageUtil.generateImgThumbnail(getFile("DarkSoul"));
        ImageUtil.generateImgThumbnail(getFile("BraveHeart.png"));
        // Thumbnailator webp不支持
        ImageUtil.generateImgThumbnail(getFile("webpImg.webp"));
        ImageUtil.generateImgThumbnail(getFile("noexist.gif"));
    }

    @Test
    public void isSupportImg() {
        Assert.assertTrue(ImageUtil.isSupportImg(getFile("空洞骑士.jpg")));
        Assert.assertFalse(ImageUtil.isSupportImg(getFile("空洞骑士.mp4")));
        Assert.assertFalse(ImageUtil.isSupportImg(getFile("DarkSoul")));
    }

    @Test
    public void isSupportImg1() {
        Assert.assertTrue(ImageUtil.isSupportImg("空洞骑士.jpg"));
        Assert.assertTrue(ImageUtil.isSupportImg("空洞骑士.png"));
        Assert.assertTrue(ImageUtil.isSupportImg("空洞骑士.gif"));

        Assert.assertFalse(ImageUtil.isSupportImg("空洞骑士"));
        Assert.assertFalse(ImageUtil.isSupportImg("空洞骑士.mp4"));
    }

    public static File getFile(String fileName) {
        String path = System.getProperty("user.dir") + "/src/test/java/resources/" + fileName;
        return new File(path);
    }

}