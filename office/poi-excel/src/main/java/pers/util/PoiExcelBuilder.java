package pers.util;

import lombok.Data;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

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
    private Map<String, CellStyle> cellStyleMap;
    /** 分隔线（合并单元格）样式 */
    private CellStyle lineStyle;
    /** 页名集合（用于防重名） */
    private Set<String> sheetNameSet = new HashSet<>();
    /** 当前页中，列中最长的值，用于调整列宽度 */
    private Map<Integer, String> columnMaxByteLenStrMap;

    /** 文档最后输出 */
    private OutputStream out;

    private static final String DEFAULT_CELL_STYLE = "default";
    private static final String WRAP_TEXT_CELL_STYLE = "wrapText";
    private static final int MAX_SHEET_NAME_LEN = 31;
    private static final String DEFAULT_SHEET_NAME = "sheet";

    public PoiExcelBuilder(OutputStream out) {

        this.out = out;

        this.wb = new HSSFWorkbook();
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setFontName("等线 (正文)");

        cellStyleMap = new HashMap<>();
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFont(font);
        cellStyleMap.put(DEFAULT_CELL_STYLE, cellStyle);

        cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        cellStyleMap.put(WRAP_TEXT_CELL_STYLE, cellStyle);

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

        /**
         * excel sheet 名字的限制
         * 名称不多于 31 个字符。
         * 名称不包含下列任一字符:  :  \  /  ?  *  [  或  ]。
         * 名称不为空。
         */
        if (sheetName == null || sheetName.isEmpty()) {
            sheetName = DEFAULT_SHEET_NAME;
        } else if (sheetName.length() >= MAX_SHEET_NAME_LEN) {
            sheetName = sheetName.substring(0, 30);
        }
        sheetName = sheetName.replaceAll("[\\\\|\\/|\\?|\\*|\\[|\\]|？]", "_");

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
     * 添加合并区域
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     * @return
     */
    public PoiExcelBuilder addCellRange(int firstRow, int lastRow, int firstCol, int lastCol) {
        CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        currentSheet.addMergedRegion(region);
        // 当前行为分割线的 下一行
        currentRowIdx = lastRow;
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
        Cell cell = row.createCell(firstCol);
        cell.setCellValue(rowValue);
        cell.setCellStyle(lineStyle);

        // 当前行为分割线的 下一行
        currentRowIdx = ++lastRow;
        return this;
    }

    public PoiExcelBuilder addWrapTextCell(int columnIdx, String ... values) {
        addCell(columnIdx, cellStyleMap.get(WRAP_TEXT_CELL_STYLE), values);
        return this;
    }

    public PoiExcelBuilder addCell(int columnIdx, String ... values) {
        addCell(columnIdx, cellStyleMap.get(DEFAULT_CELL_STYLE), values);
        return this;
    }

    public PoiExcelBuilder addCellWithCustomStyle(int columnIdx, String customStyle, String ... values) {
        addCell(columnIdx, cellStyleMap.get(customStyle), values);
        return this;
    }

    /**
     * 为表格填值
     * @param columnIdx
     * @param cellStyle
     * @param values
     * @return
     */
    private PoiExcelBuilder addCell(int columnIdx, CellStyle cellStyle, String ... values) {

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
     * 自定义样式
     * @param customStyle
     * @return
     */
    public String createCustomStyle(CustomStyle customStyle) {

        Font font = wb.createFont();
        font.setFontName(customStyle.getFontName());
        font.setFontHeightInPoints(customStyle.getFontSize());

        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.forInt(customStyle.getHorizontalAlignment()));
        cellStyle.setVerticalAlignment(VerticalAlignment.forInt(customStyle.getVerticalAlignment()));
        cellStyle.setWrapText(customStyle.isWrapText());
        cellStyle.setFont(font);

        if (customStyle.getFillForegroundColor() != null) {
            cellStyle.setFillForegroundColor(customStyle.getFillForegroundColor());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }

        String key = UUID.randomUUID().toString();
        cellStyleMap.put(key, cellStyle);
        return key;
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
    public void finish() throws IOException {

        wb.write(out);

        // help gc
        currentSheet = null;
        currentRow = null;
        wb = null;
        out = null;
    }

    public PoiExcelBuilder rowIdxDecrement() {
        this.currentRowIdx--;
        return this;
    }

    public PoiExcelBuilder rowIdxDecrement(int num) {
        this.currentRowIdx -= num;
        return this;
    }

    public PoiExcelBuilder rowIdxIncrement() {
        this.currentRowIdx++;
        return this;
    }

    public PoiExcelBuilder rowIdxIncrement(int num) {
        this.currentRowIdx += num;
        return this;
    }

    public int getCurrentRowIdx() {
        return currentRowIdx;
    }

    private int getWidth(String text) {
        return (int) Math.min((text.getBytes().length * 0.5d + 1) * 800, 7000);
    }

    /**
     * 自定义样式
     */
    @Data
    public static class CustomStyle {

        public final static int HORIZONTAL_ALIGNMENT_LEFT = HorizontalAlignment.LEFT.getCode();
        public final static int HORIZONTAL_ALIGNMENT_CENTER = HorizontalAlignment.CENTER.getCode();

        public final static int VERTICAL_ALIGNMENT_CENTER = VerticalAlignment.CENTER.getCode();

        public final static short FILL_FOREGROUND_COLOR_LIGHT_GREEN = HSSFColor.HSSFColorPredefined.LIGHT_GREEN.getIndex();
        public final static short FILL_FOREGROUND_COLOR_GREY = HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex();

        /** 水平排列 */
        private int horizontalAlignment = HORIZONTAL_ALIGNMENT_LEFT;
        /** 垂直排列 */
        private int verticalAlignment = VERTICAL_ALIGNMENT_CENTER;
        /** 字体 */
        private String fontName = "宋体";
        /** 字体大小 */
        private short fontSize = 12;
        /** 是否自动换行 */
        private boolean wrapText = false;
        /** 前景色 */
        private Short fillForegroundColor;

    }

}
