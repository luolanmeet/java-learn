package pers.groovy;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @auther ken.ck
 * @date 2021/10/15 12:54
 */
public class Tmp4Groovy {

    public static void main(String[] args) throws Exception {

        String[] filePaths = {
                "/Users/chenken/Downloads/执行结果1.xlsx",
                "/Users/chenken/Downloads/执行结果1 (1).xlsx",
                "/Users/chenken/Downloads/执行结果1 (2).xlsx",
        };

        for (String filePath : filePaths) {
            checkoutGroovy(filePath);
        }
    }

    public static void checkoutGroovy(String filePath) throws Exception {

        InputStream is = new FileInputStream(filePath);
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        // 遍历所有行
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            XSSFRow row = sheet.getRow(i);
            // 脚本内容在第x列  索引从0开始计算
            XSSFCell cell = row.getCell(4);
            String groovy = cell.getStringCellValue();

            List<String> checkResult = CheckGroovyUtil.check(groovy);

            if (checkResult.isEmpty()) {
                continue;
            }

            String groovyId = row.getCell(0).getStringCellValue();
            String groovyName = row.getCell(1).getStringCellValue();

            System.out.println("----脚本id：" +groovyId + "------脚本名称：" + groovyName+"------");
            checkResult.forEach(System.out::println);
            System.out.println();
        }
    }

}
