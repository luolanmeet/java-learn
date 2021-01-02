package test;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @auther ken.ck
 * @date 2021/1/2 15:12
 */
public class Tmp4TFX {

    public static void main(String[] args) throws Exception {
        InputStream is = new FileInputStream("/Users/chenken/Downloads/成聪待发(1).xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        String sqlPattern =
                "SELECT `sale_order_code`, `trade_code` " +
                        "FROM `sale_order` WHERE `platform` = 836 " +
                        "and `out_shop_id` = {0} and `trade_code` in ({1});";

        int size = 200;
        int count = 0;

        List<String> sqls = new ArrayList<>();
        String outShopId = "\"" + "abc" + "\"" ;
        StringJoiner temOrderIds = new StringJoiner(",");

        // 遍历所有行
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            if (count == 200) {
                sqls.add(MessageFormat.format(sqlPattern, outShopId, temOrderIds.toString()));
                temOrderIds = new StringJoiner(",");
                count = 0;
            }

            count++;

            XSSFRow row = sheet.getRow(i);
            // 销售单号在第x列  索引从0开始计算
            XSSFCell cell = row.getCell(1);

            if (cell == null) {
                continue;
            }

            String orderId = cell.getStringCellValue();
            if (orderId == null || orderId.isEmpty()) {
                continue;
            }

            temOrderIds.add("\"" + orderId + "\"");
        }

        if (count != 0) {
            sqls.add(MessageFormat.format(sqlPattern, outShopId, temOrderIds.toString()));
        }

        sqls.forEach(System.out::println);
    }

}
