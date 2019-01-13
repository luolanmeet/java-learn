package com.create.suoyuan;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 
 * @author cck
 */
public class PDFCreater {
    
    // 用于显示中文的 Font
    static Font chineseFont;
    
    static {
        BaseFont bfChinese;
        try {
            bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            chineseFont = new Font(bfChinese); 
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
    
    // 背景图片
    final static String BACKGROUND_PIC = "bg.jpg";
    
    // 文件生成之后保存的路径
    final static String PDF_PATH_NAME = "d:\\TestItext.pdf";
    
    // 背景颜色
    final static BaseColor BACKGROUND_COLOR = new BaseColor(253, 195, 193, 0);
    
    public static void create(List<Diray> dirays, User user) {
        
        // 页面大小  
        Rectangle rect = new Rectangle(PageSize.A4.rotate());  
        // 页面背景色  
        // rect.setBackgroundColor(BACKGROUND_COLOR);
          
        Document doc = new Document(rect);  
          
        try {
            
            FileOutputStream out = new FileOutputStream(PDF_PATH_NAME);
            PdfWriter writer = PdfWriter.getInstance(doc, out);  
            
            //PDF版本(默认1.4)  
            writer.setPdfVersion(PdfWriter.PDF_VERSION_1_2);  
            
            // 要存入信息需要先调用open
            doc.open();  
            
            //设置文档属性  
            setDocAttribute(user, doc);  
                
            // 添加头像 画了个图
            addUserMsg(doc, user);  
            
            // 普通的
            Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD);
            
            for (Diray diray : dirays) {
                
                Paragraph time = new Paragraph(diray.getTime(), chapterFont);
                Paragraph content = new Paragraph(diray.getContent(), chineseFont);
                content.setSpacingAfter(10);
                doc.add(time);
                doc.add(content);
                // 画背景图
                drawBgPic(writer);
            }
            
            doc.newPage();
            
            doc.close();  
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    /**
     * 每一页都得画 有点烦
     * @param writer
     * @throws Exception
     */
    private static void drawBgPic(PdfWriter writer) throws Exception {
        
        PdfContentByte canvas = writer.getDirectContentUnder();
        Image image = Image.getInstance(BACKGROUND_PIC);
        image.scaleAbsolute(PageSize.A4.rotate());
        image.setAbsolutePosition(0, 0);
        canvas.addImage(image);
    }

    /**
     * 设置PDF文档属性
     * @param user
     * @param doc
     */
    private static void setDocAttribute(User user, Document doc) {
        doc.addTitle("diray");  
        doc.addAuthor(user.getNickname());  
        doc.addSubject("suoyuan");  
        doc.addKeywords("diray");  
        doc.addCreator("suoyuan");
    }

    private static void addUserMsg(Document doc, User user) throws Exception {
        
        Image img = Image.getInstance(user.getAvatar());  
        img.setAlignment(Image.LEFT | Image.TEXTWRAP);  
        img.setBorder(Image.BOX);  
        img.setBorderWidth(10);  
        img.setBorderColor(BaseColor.WHITE);  
        // 大小
        img.scaleToFit(112, 112);  
        // 旋转
        // img.setRotationDegrees(1);  
        doc.add(img);
        
        Paragraph name = new Paragraph(user.getNickname(), chineseFont);
        name.setIndentationLeft(20);
        name.setSpacingBefore(30);
        doc.add(name);
        Paragraph signature = new Paragraph(user.getSignature(), chineseFont);
        signature.setIndentationLeft(20);
        signature.setSpacingBefore(4);
        doc.add(signature);
        
        // 换个行
        Paragraph line = new Paragraph("\n");
        line.setSpacingBefore(62);
        doc.add(line);
    }
    
}
