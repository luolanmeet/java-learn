package pers.util;

import lombok.Data;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * @auther ken.ck
 * @date 2022/12/8 17:14
 */
@Data
public class TemplateStyle {

    private ParagraphAlignment alignment;
    private String style;
    private Double fontSize;
    private String fontFamily;
    private Boolean bold;
    private String color;
    private Boolean capitalized;
    private Boolean shadowed;

    public TemplateStyle(XWPFParagraph templateParagraph) {

        this.alignment = templateParagraph.getAlignment();
        this.style = templateParagraph.getStyleID();

        XWPFRun templateRun = templateParagraph.getRuns().get(0);

        this.fontSize = templateRun.getFontSizeAsDouble();
        this.fontFamily = templateRun.getFontFamily();
        this.bold = templateRun.isBold();
        this.color = templateRun.getColor();
        this.capitalized = templateRun.isCapitalized();
        this.shadowed = templateRun.isShadowed();
    }
}
