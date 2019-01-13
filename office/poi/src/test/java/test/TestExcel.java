package test;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

/**
 * 
 * @author cck
 */
public class TestExcel {
    
    @Test
    public void testCreateExcel() throws IOException {
        
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("三年级（1）班学生名单");
        // 使用 Sheet 接口创建一行
        Row row = sheet.createRow(0);
        Cell c1 = row.createCell(0);
        // 使用重载的方法为单元格设置值
        c1.setCellValue(1);
        Cell c2 = row.createCell(1);
        c2.setCellValue(1.2);
        Cell c3 = row.createCell(2);
        c3.setCellValue("伟大的 POI 啊");
        Cell c4 = row.createCell(3);
        c4.setCellValue(false);
        FileOutputStream fos = new FileOutputStream("d:\\POI使用FileOutputStream输出流生成的工作簿.xls");
        wb.write(fos);
        fos.close();
        wb.close();
    }
}
