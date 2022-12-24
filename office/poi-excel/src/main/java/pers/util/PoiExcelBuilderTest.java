package pers.util;

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

        try (FileOutputStream fos = new FileOutputStream(completePath);) {

            PoiExcelBuilder builder = new PoiExcelBuilder(fos);

            builder.newSheet("sheetName");

            PoiExcelBuilder.CustomStyle customStyle = new PoiExcelBuilder.CustomStyle();
            customStyle.setFontSize((short) 30);
            customStyle.setFillForegroundColor(PoiExcelBuilder.CustomStyle.FILL_FOREGROUND_COLOR_GREY);
            String customStyleKey = builder.createCustomStyle(customStyle);

            builder.addSplitLine(builder.getCurrentRowIdx(), builder.getCurrentRowIdx(),0, 6, "基本信息")
                    .newRow().addCell(0, "xx：", "xxx")
                    .newRow().addCell(0, "xx：", "xxx")
                    .newRow().addCell(0, "xx：", "xxx")
                    .newRow().addCellWithCustomStyle(0, customStyleKey,"xx：", "xxx");

            builder.rowIdxIncrement().rowIdxIncrement()
                    .addSplitLine(builder.getCurrentRowIdx(), builder.getCurrentRowIdx(), 0, 6, "请求参数")
                    .newRow().addCell(0, "xx", "xx", "xx", "xx", "xx", "xx", "xx");
            // ...

            builder.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
