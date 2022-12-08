package pers.util;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther ken.ck
 * @date 2022/12/7 15:57
 */
public class PoiWordBuilder {

    public final static String TEMPLATE_TITLE = "TEMPLATE_TITLE";
    public final static String TEMPLATE_HEAD_1 = "TEMPLATE_HEAD_1";
    public final static String TEMPLATE_HEAD_2 = "TEMPLATE_HEAD_2";
    public final static String TEMPLATE_BODY = "TEMPLATE_BODY";

    private Map<String, TemplateStyle> templateMap = new HashMap<>();

    private XWPFDocument doc;

    public PoiWordBuilder(String templatePath) {

        try (FileInputStream in = new FileInputStream(templatePath);) {
            doc = new XWPFDocument(in);
            // 解析模板文件中的格式
            while (doc.getParagraphs().size() > 0) {
                XWPFParagraph paragraph = doc.getParagraphs().get(0);
                String text = paragraph.getText();
                if (text != null && !"".equals(text)) {
                    templateMap.put(paragraph.getText(), new TemplateStyle(paragraph));
                }
                doc.removeBodyElement(0);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String savePath) {
        try (FileOutputStream out = new FileOutputStream(savePath)) {
            doc.write(out);
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PoiWordBuilder addBlankLine() {
        doc.createParagraph();
        return this;
    }

    public PoiWordBuilder addTextUseTemplateStyle(String text, String templateStylekey) {

        TemplateStyle templateStyle = templateMap.get(templateStylekey);

        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.setAlignment(templateStyle.getAlignment());
        paragraph.setStyle(templateStyle.getStyle());

        XWPFRun run = paragraph.createRun();
        run.setText(text);
        if (templateStyle.getFontSize() != null) {
            run.setFontSize(templateStyle.getFontSize());
        }
        run.setFontFamily(templateStyle.getFontFamily());
        run.setBold(templateStyle.getBold());
        run.setColor(templateStyle.getColor());
        run.setCapitalized(templateStyle.getCapitalized());
        run.setShadow(templateStyle.getShadowed());

        return this;
    }

}
