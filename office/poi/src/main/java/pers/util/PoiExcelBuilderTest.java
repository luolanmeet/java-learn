package pers.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;

/**
 * @auther ken.ck
 * @date 2022/7/7 20:45
 */
public class PoiExcelBuilderTest {

    public static void main(String[] args) {

        // 存储文件
        String savePath = System.getProperty("user.dir") + "/";
        String fileName = "excel" + System.currentTimeMillis() + ".xls";
        String completePath = savePath + fileName;

        try (FileOutputStream fos = new FileOutputStream(completePath);
             Workbook wb = new HSSFWorkbook()) {

            PoiExcelBuilder builder = new PoiExcelBuilder(wb);

            builder.newSheet("sheetName");

            builder.addSplitLine(builder.getCurrentRowIdx(), builder.getCurrentRowIdx(),0, 6, "基本信息")
                    .newRow().addCell(0, "xx：", "xxx")
                    .newRow().addCell(0, "xx：", "xxx")
                    .newRow().addCell(0, "xx：", "xxx")
                    .newRow().addCell(0, "xx：", "xxx");

            builder.rowIdxIncrement().rowIdxIncrement()
                    .addSplitLine(builder.getCurrentRowIdx(), builder.getCurrentRowIdx(), 0, 6, "请求参数")
                    .newRow().addCell(0, "xx", "xx", "xx", "xx", "xx", "xx", "xx");
            // ...

            builder.finish();
            wb.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
