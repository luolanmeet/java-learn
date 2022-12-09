package pers.word2html;

import fr.opensagres.poi.xwpf.converter.xhtml.Base64EmbedImgManager;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

/**
 * @auther ken.ck
 * @date 2022/12/9 11:29
 */
public class Word2HtmlConverter {

    public static void main(String[] args) throws IOException {

        String basePath = System.getProperty("user.dir") + "/office/poi-word/";
        // 不能转 用poi生成的 word 文档，有 NPE
        // https://github.com/opensagres/xdocreport/issues/155
        String docPath = basePath + "poiWord.docx";
        String htmlPath = basePath + "poiWord.html";

        try (InputStream in = new FileInputStream(docPath);
             OutputStream out = new FileOutputStream(htmlPath);
             XWPFDocument document = new XWPFDocument(in);) {

            ZipSecureFile.setMinInflateRatio(-1.0d);
            XHTMLOptions options = XHTMLOptions.create();
            //  图片转为base64编码
            options.setImageManager(new Base64EmbedImgManager());
            XHTMLConverter.getInstance().convert(document, out, options);
        }
    }

}
