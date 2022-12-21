package pers.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;

/**
 * @auther ken.ck
 * @date 2022/12/12 11:12
 */
public class PdfboxBuilderTest {

    public static void main(String[] args) {

        String filename = "pdf/helloworld.pdf";
        String ttcFile = "pdf/songti.ttf";
        String message = "你好呀 hello";

        try (PDDocument doc = new PDDocument();) {

            PDPage page = new PDPage();
            doc.addPage(page);

//            PDFont font = PDType1Font.HELVETICA_BOLD;
            PDType0Font font = PDType0Font.load(doc, new File(ttcFile));

            PDPageContentStream contents = new PDPageContentStream(doc, page);
            contents.beginText();
            contents.setFont(font, 20);
            contents.setLineWidth(2);
            contents.newLineAtOffset(100, 0);
            contents.showText(message);
            contents.endText();

            contents.beginText();
            contents.newLine();
            contents.setFont(font, 20);
            contents.setLineWidth(1);
            contents.newLineAtOffset(100, 20);
            contents.showText("hello");
            contents.endText();

            contents.close();

            doc.save(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
