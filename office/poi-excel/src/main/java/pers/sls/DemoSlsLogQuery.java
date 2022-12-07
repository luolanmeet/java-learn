package pers.sls;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.Row;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 示例
 * @auther ken.ck
 */
public class DemoSlsLogQuery extends AbstractSlsLogQuery {

    public static void main(String[] args) throws Exception {

        String fileName = "收集数据";
        String startFromStr = "2021-11-11 00:00:00";
        String toFromStr = "2021-11-11 00:07:00";
        String keyWord = "xxx and Remote service error";

        DemoSlsLogQuery query = new DemoSlsLogQuery();
        query.queryAndW2File(fileName, startFromStr, toFromStr, keyWord);
        query.queryAndW2Excel(fileName, startFromStr, toFromStr, keyWord);
    }

    @Override
    public String handleLog(JSONObject dataJson) throws Exception {

        String body = dataJson.getJSONArray("args").getJSONObject(0)
                .getJSONObject("requestDataObject")
                .getJSONObject("body")
                .getString("data");

        Document document = DocumentHelper.parseText(body);
        Element rootElm = document.getRootElement();
        Element orderCodeElm = rootElm.element("orderCode");
        return orderCodeElm.getText();
    }

    @Override
    public void handleLog(JSONObject dataJson, Row row) throws Exception {

        String body = dataJson.getJSONArray("args").getJSONObject(0)
                .getJSONObject("requestDataObject")
                .getJSONObject("body")
                .getString("data");

        Document document = DocumentHelper.parseText(body);
        Element rootElm = document.getRootElement();
        Element orderCodeElm = rootElm.element("orderCode");
        Element orderTypeElm = rootElm.element("orderType");
        Element operateDateElm = rootElm.element("operateDate");

        row.createCell(0).setCellValue(orderCodeElm.getText());
        row.createCell(1).setCellValue(orderTypeElm.getText());
        row.createCell(2).setCellValue(operateDateElm.getText());
    }

}
