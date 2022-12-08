package pers.util;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther ken.ck
 * @date 2022/12/7 15:57
 */
public class PoiWordBuilder {

    private int templateTableIdx = 1;
    private final static String TEMPLATE_TABLE_HEAD_CELL = "TEMPLATE_TABLE_HEAD_CELL_";
    private final static String TEMPLATE_TABLE_DATA_CELL = "TEMPLATE_TABLE_DATA_CELL_";

    private Map<String, TemplateStyle> templateMap = new HashMap<>();

    private XWPFDocument doc;

    public PoiWordBuilder(String templatePath) {

        try (FileInputStream in = new FileInputStream(templatePath);) {

            doc = new XWPFDocument(in);
            templateTableIdx = 1;

            // 保存表格数据格式
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

            // 保存段落数据格式
            while (doc.getParagraphs().size() > 0) {
                XWPFParagraph paragraph = doc.getParagraphs().get(0);
                String text = paragraph.getText();
                if (text != null && !"".equals(text)) {
                    templateMap.put(paragraph.getText(), new TemplateStyle(paragraph));
                }
                doc.removeBodyElement(0);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PoiWordBuilder addBlankLine() {
        doc.createParagraph();
        return this;
    }

    public PoiWordBuilder addTable(String [] tableHead, List<String[]> tableDatas, Integer templateTableStyleIdx) {

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

    private void setXwpfRunStyle(XWPFRun xwpfRun, TemplateStyle templateStyle) {

        if (templateStyle == null) {
            return ;
        }

        if (templateStyle.getFontSize() != null) {
            xwpfRun.setFontSize(templateStyle.getFontSize());
        }
        xwpfRun.setFontFamily(templateStyle.getFontFamily());
        xwpfRun.setBold(templateStyle.getBold());
        xwpfRun.setColor(templateStyle.getColor());
        xwpfRun.setCapitalized(templateStyle.getCapitalized());
        xwpfRun.setShadow(templateStyle.getShadowed());
    }

    public PoiWordBuilder addTextWithStyle(String[] texts, String templateStyleKey) {
        for (String text : texts) {
            addTextWithStyle(text, templateStyleKey);
        }
        return this;
    }

    public PoiWordBuilder addTextWithStyle(String text, String templateStyleKey) {

        TemplateStyle templateStyle = templateMap.get(templateStyleKey);

        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.setAlignment(templateStyle.getAlignment());
        paragraph.setStyle(templateStyle.getStyle());

        XWPFRun xwpfRun = paragraph.createRun();
        xwpfRun.setText(text);
        setXwpfRunStyle(xwpfRun, templateStyle);

        return this;
    }

    public void write(String savePath) {
        try (FileOutputStream out = new FileOutputStream(savePath)) {
            doc.write(out);
            doc.close();
            this.doc = null;
            this.templateMap = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
