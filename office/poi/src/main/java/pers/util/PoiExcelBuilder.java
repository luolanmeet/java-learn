package pers.util;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 辅助构造 poi excel 文件
 * @auther ken.ck
 * @date 2022/7/6 18:36
 */
public class PoiExcelBuilder {

    /** poi excel文档对象 */
    private Workbook wb;
    /** 当前 excel 页 */
    private Sheet currentSheet;
    /** 当前行 */
    private Row currentRow;
    /** 当前行数 */
    private int currentRowIdx;
    /** 表格样式 */
    private CellStyle cellStyle;
    /** 分隔线（合并单元格）样式 */
    private CellStyle lineStyle;
    /** 文字样式 */
    private Font font;
    /** 页名集合（用于防重名） */
    private Set<String> sheetNameSet = new HashSet<>();
    /** 当前页中，列中最长的值，用于调整列宽度 */
    private Map<Integer, String> columnMaxByteLenStrMap;

    public PoiExcelBuilder(Workbook wb) {

        this.wb = wb;

        font = wb.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setFontName("等线 (正文)");

        cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setFont(font);

        lineStyle = wb.createCellStyle();
        lineStyle.setAlignment(HorizontalAlignment.CENTER);
        lineStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_GREEN.getIndex());
        lineStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        lineStyle.setFont(font);
    }

    /**
     * 新建一个页面
     * @param sheetName
     * @return
     */
    public PoiExcelBuilder newSheet(String sheetName) {

        int tmpCount = 0;
        String tmpSheetName = sheetName;
        while (sheetNameSet.contains(tmpSheetName)) {
            tmpSheetName = sheetName + tmpCount++;
        }
        sheetNameSet.add(tmpSheetName);
        currentSheet = wb.createSheet(tmpSheetName);
        currentRow = null;
        currentRowIdx = 0;
        columnMaxByteLenStrMap = new HashMap<>();
        return this;
    }

    /**
     * 新建一行，并作为当前行
     * @return
     */
    public PoiExcelBuilder newRow() {
        currentRow = currentSheet.createRow(currentRowIdx++);
        return this;
    }

    /**
     * 添加分割线
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     * @param rowValue
     * @return
     */
    public PoiExcelBuilder addSplitLine(int firstRow, int lastRow, int firstCol, int lastCol,
                             String rowValue) {

        CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        currentSheet.addMergedRegion(region);

        Row row = currentSheet.createRow(lastRow);
        Cell cell = row.createCell(0);
        cell.setCellValue(rowValue);
        cell.setCellStyle(lineStyle);

        // 当前行为分割线的 下一行
        currentRowIdx = ++lastRow;
        return this;
    }

    /**
     * 为表格填值
     * @param columnIdx
     * @param values
     * @return
     */
    public PoiExcelBuilder addCell(int columnIdx, String ... values) {

        for (String value : values) {

            if (value == null) {
                columnIdx++;
                continue;
            }

            String maxLenStr = columnMaxByteLenStrMap.getOrDefault(columnIdx, "");
            if (value.getBytes().length > maxLenStr.getBytes().length) {
                columnMaxByteLenStrMap.put(columnIdx, value);
            }
            Cell cell  = currentRow.createCell(columnIdx++);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(value);
        }
        return this;
    }

    /**
     * 当前页完成，自动根据页中的数据调整列的宽度大小
     * @return
     */
    public PoiExcelBuilder finishSheet() {

        if (currentSheet == null || columnMaxByteLenStrMap == null) {
            return this;
        }

        // 设置列宽度
        for (Map.Entry<Integer, String> entry : columnMaxByteLenStrMap.entrySet()) {
            currentSheet.setColumnWidth(entry.getKey(), getWidth(entry.getValue()));
        }

        return this;
    }

    /**
     * excel 编辑完成
     */
    public void finish() {
        // help gc
        currentSheet = null;
        currentRow = null;
        wb = null;
    }

    public PoiExcelBuilder rowIdxIncrement() {
        this.currentRowIdx++;
        return this;
    }

    public int getCurrentRowIdx() {
        return currentRowIdx;
    }

    private int getWidth(String text) {
        return (int) Math.min((text.getBytes().length * 0.5d + 1) * 800, 7000);
    }

}
