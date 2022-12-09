package pers.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.OutputStream;

/**
 * @auther ken.ck
 * @date 2022/12/9 15:57
 */
public class ItextPdfBuilder {

    private Document doc;

    private PdfWriter writer;

    private Font chineseFont;
    private BaseFont bfChinese;

    public ItextPdfBuilder(OutputStream out) throws Exception {

        bfChinese = BaseFont.createFont(
                "STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        chineseFont = new Font(bfChinese);

        Rectangle rect = new Rectangle(PageSize.A4.rotate());
        doc = new Document(rect);
        writer = PdfWriter.getInstance(doc, out);
        writer.setPdfVersion(PdfWriter.PDF_VERSION_1_2);
        doc.open();
    }

    public ItextPdfBuilder addBlankLine() throws Exception {
        Paragraph content = new Paragraph("\n");
        content.setSpacingAfter(10);
        doc.add(content);
        return this;
    }

    public ItextPdfBuilder addTitle(String title) throws Exception {

        Chunk chapTitle = new Chunk("The  chapter");
        Chapter chapter = new Chapter(new Paragraph(chapTitle), 1);
        doc.add(chapter);

        Font font = new Font(bfChinese, 34, Font.BOLD);
        Paragraph content = new Paragraph(title, font);
        // 居中
        content.setAlignment(Paragraph.ALIGN_CENTER);

        doc.add(content);
        return this;
    }

    public void finish() {
        doc.newPage();
        doc.close();
        doc = null;
        writer.close();
        writer = null;
        chineseFont = null;
    }

}
