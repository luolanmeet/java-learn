package test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.junit.Test;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * http://rensanning.iteye.com/blog/1538689
 * @author cck
 */
public class TestItext {
    
    String PDF_PATH_NAME = "d:\\TestItext.pdf";
    
    @Test
    public void testCreatePDF1() throws FileNotFoundException, DocumentException {
        
      //Step 1—Create a Document.  
      Document document = new Document();  
      
      //Step 2—Get a PdfWriter instance.  
      PdfWriter.getInstance(document, new FileOutputStream(PDF_PATH_NAME));  
      
      //Step 3—Open the Document.  
      document.open();  
      
      //Step 4—Add content.  
      document.add(new Paragraph("Hello World"));  
      
      //Step 5—Close the Document.  
      document.close();  
    }
    
    @Test
    public void testCreatePDF2() throws FileNotFoundException, DocumentException {
        // 页面大小  
        Rectangle rect = new Rectangle(PageSize.B5.rotate());  
        // 页面背景色  
        rect.setBackgroundColor(BaseColor.ORANGE);  
          
        Document doc = new Document(rect);  
          
        FileOutputStream out = new FileOutputStream(PDF_PATH_NAME);
        
        PdfWriter writer = PdfWriter.getInstance(doc, out);  
          
        //PDF版本(默认1.4)  
        writer.setPdfVersion(PdfWriter.PDF_VERSION_1_2);  
          
        //文档属性  
        doc.addTitle("Title@sample");  
        doc.addAuthor("cck");  
        doc.addSubject("suoyuan");  
        doc.addKeywords("pdf");  
        doc.addCreator("cck");  
          
        //页边空白  
        doc.setMargins(10, 20, 30, 40);  
          
        doc.open();  
        doc.add(new Paragraph("Hello World"));  
        doc.close();  
    }
    
}
