package pers.util;

import lombok.Data;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther ken.ck
 * @date 2022/12/7 15:57
 */
public class PoiWordBuilder {

    /** 表格头模板 样式名前缀 */
    private final static String TEMPLATE_TABLE_HEAD_CELL = "TEMPLATE_TABLE_HEAD_CELL_";
    /** 表格内容模板 样式名前缀 */
    private final static String TEMPLATE_TABLE_DATA_CELL = "TEMPLATE_TABLE_DATA_CELL_";

    /** 暂存模板的样式 */
    private Map<String, TemplateStyle> templateMap = new HashMap<>();
    /** 文档对象 */
    private XWPFDocument doc;
    /** 文档最后输出 */
    private OutputStream out;

    /**
     * 创建构造实例，需传入使用的样式模板
     * @param templatePath
     * @throws IOException
     */
    public PoiWordBuilder(String templatePath, OutputStream out) throws IOException {

        this.out = out;

        // 读取传入的模板文件，暂存样式
        try (InputStream in = PoiWordBuilder.class.getClassLoader().getResourceAsStream(templatePath);) {

            // 基于传入的文档创建新文档
            doc = new XWPFDocument(in);

            // 保存表格数据格式，样式名为 默认表格前缀+下标
            int templateTableIdx = 1;
            List<XWPFTable> tables = doc.getTables();
            if (tables != null && tables.size() > 0) {
                for (XWPFTable table : tables) {
                    List<XWPFTableRow> rows = table.getRows();
                    if (rows != null && rows.size() > 0) {
                        XWPFTableRow row = rows.get(0);
                        saveTableCellStyle(row, TEMPLATE_TABLE_HEAD_CELL + templateTableIdx);
                    }
                    if (rows != null && rows.size() > 1) {
                        XWPFTableRow row = rows.get(1);
                        saveTableCellStyle(row, TEMPLATE_TABLE_DATA_CELL + templateTableIdx);
                    }
                    templateTableIdx++;
                }
            }

            // 保存样式，样式名为 模板的文本
            while (doc.getParagraphs().size() > 0) {
                XWPFParagraph paragraph = doc.getParagraphs().get(0);
                String text = paragraph.getText();
                if (text != null && !"".equals(text)) {
                    templateMap.put(paragraph.getText(), new TemplateStyle(paragraph));
                }
                // 删除模板的内容
                doc.removeBodyElement(0);
            }
        }
    }

    /**
     * 添加空行
     * @return
     */
    public PoiWordBuilder addBlankLine() {
        doc.createParagraph();
        return this;
    }

    /**
     * 添加表格数据
     * @param tableHead
     * @param tableDatas
     * @param templateTableStyleIdx
     * @return
     */
    public PoiWordBuilder addTable(String [] tableHead, List<String[]> tableDatas, Integer templateTableStyleIdx) {

        // 获取表格样式
        TemplateStyle headRowStyle = templateMap.get(TEMPLATE_TABLE_HEAD_CELL + templateTableStyleIdx);
        TemplateStyle dataRowStyle = templateMap.get(TEMPLATE_TABLE_DATA_CELL+ templateTableStyleIdx);

        // 设置表头数据
        XWPFTable table = doc.createTable();
        XWPFTableRow headRow = table.getRow(0);
        for (int i = 0; i < tableHead.length; i++) {
            XWPFTableCell cell = null;
            if (i == 0) {
                cell = headRow.getCell(0);
            } else {
                cell = headRow.addNewTableCell();
            }
            cell.setText(tableHead[i]);
            XWPFRun xwpfRun = cell.getParagraphs().get(0).getRuns().get(0);
            setXwpfRunStyle(xwpfRun, headRowStyle);
        }

        // 设置表数据
        for (String[] tableData : tableDatas) {
            XWPFTableRow dataRow = table.createRow();
            for (int i = 0; i < tableData.length; i++) {
                XWPFTableCell cell = dataRow.getCell(i);
                cell.setText(tableData[i]);
                XWPFRun xwpfRun = cell.getParagraphs().get(0).getRuns().get(0);
                setXwpfRunStyle(xwpfRun, dataRowStyle);
            }
        }

        return this;
    }

    /**
     * 添加文本
     * @param texts
     * @param templateStyleKey
     * @return
     */
    public PoiWordBuilder addTextWithStyle(String[] texts, String templateStyleKey) {
        for (String text : texts) {
            addTextWithStyle(text, templateStyleKey);
        }
        return this;
    }

    public PoiWordBuilder addTextWithStyle(List<String> texts, String templateStyleKey) {
        if (texts == null || texts.isEmpty()) {
            return this;
        }
        for (String text : texts) {
            addTextWithStyle(text, templateStyleKey);
        }
        return this;
    }

    /**
     * 添加文本
     * @param text
     * @param templateStyleKey
     * @return
     */
    public PoiWordBuilder addTextWithStyle(String text, String templateStyleKey) {

        // 获取样式
        TemplateStyle templateStyle = templateMap.get(templateStyleKey);

        // 创建段落
        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.setAlignment(templateStyle.getAlignment());
        paragraph.setStyle(templateStyle.getStyle());

        // 创建行
        XWPFRun xwpfRun = paragraph.createRun();
        xwpfRun.setText(text);

        // 设置行样式
        setXwpfRunStyle(xwpfRun, templateStyle);

        return this;
    }

    /**
     * 编辑完成
     * @param finish
     */
    public void finish() throws IOException {

        doc.write(out);
        doc.close();

        this.doc = null;
        this.out = null;
        this.templateMap = null;
    }

    /**
     * 设置表格样式
     * @param headRow
     * @param templateStyleKey
     */
    private void saveTableCellStyle(XWPFTableRow headRow, String templateStyleKey) {
        List<XWPFTableCell> tableCells = headRow.getTableCells();

        if (tableCells == null || tableCells.isEmpty()) {
            return ;
        }

        XWPFTableCell xwpfTableCell = tableCells.get(0);
        List<XWPFParagraph> paragraphs = xwpfTableCell.getParagraphs();
        if (paragraphs == null || paragraphs.isEmpty()) {
            return ;
        }

        XWPFParagraph xwpfParagraph = paragraphs.get(0);
        templateMap.put(templateStyleKey, new TemplateStyle(xwpfParagraph));
    }

    /**
     * 设置样式
     * @param xwpfRun
     * @param templateStyle
     */
    private void setXwpfRunStyle(XWPFRun xwpfRun, TemplateStyle templateStyle) {

        if (templateStyle == null) {
            return ;
        }

        if (templateStyle.getFontSize() != null) {
            xwpfRun.setFontSize(templateStyle.getFontSize());
        }
        // 字体
        xwpfRun.setFontFamily(templateStyle.getFontFamily());
        // 是否加粗
        xwpfRun.setBold(templateStyle.getBold());
        // 颜色
        xwpfRun.setColor(templateStyle.getColor());
        // 首字母大写
        xwpfRun.setCapitalized(templateStyle.getCapitalized());
        // 阴影
        xwpfRun.setShadow(templateStyle.getShadowed());
    }

    @Data
    public static class TemplateStyle {

        private ParagraphAlignment alignment;
        private String style;
        private Double fontSize;
        private String fontFamily;
        private Boolean bold;
        private String color;
        private Boolean capitalized;
        private Boolean shadowed;

        public TemplateStyle(XWPFParagraph templateParagraph) {

            this.alignment = templateParagraph.getAlignment();
            this.style = templateParagraph.getStyleID();

            XWPFRun templateRun = templateParagraph.getRuns().get(0);

            this.fontSize = templateRun.getFontSizeAsDouble();
            this.fontFamily = templateRun.getFontFamily();
            this.bold = templateRun.isBold();
            this.color = templateRun.getColor();
            this.capitalized = templateRun.isCapitalized();
            this.shadowed = templateRun.isShadowed();
        }
    }
}
