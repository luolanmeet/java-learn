package pers.sls;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.log.Client;
import com.aliyun.openservices.log.common.LogContent;
import com.aliyun.openservices.log.common.QueriedLog;
import com.aliyun.openservices.log.response.GetLogsResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther ken.ck
 */
public abstract class AbstractSlsLogQuery {

    private static final Map<String,String> CONTENT_KEY_CONVERT = new HashMap<String,String>() {{
        put("__tag__:__receive_time__", "receiveTime");
    }};
    private static final String EXTEND_MAP = "extendMap";
    private static final String DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 需要修改，则在子类中覆盖
     */
    protected String slsUrl = ""; // 必填
    protected String accessKeyId = ""; // 必填
    protected String accessKeySecret = ""; // 必填
    protected String project = ""; // 必填
    protected String logStore = ""; // 必填
    protected String topic = "";
    protected boolean reverse = true;

    private BufferedWriter bw = null;
    private Client client = null;

    public abstract String handleLog(JSONObject dataJson) throws Exception;
    public abstract void handleLog(JSONObject dataJson, Row row) throws Exception;

    /**
     * 查询日志并写入excel
     * @param fileName
     * @param startFromStr
     * @param toFromStr
     * @param keyWord
     * @throws Exception
     */
    protected void queryAndW2Excel(String fileName,
                                  String startFromStr, String toFromStr,
                                  String keyWord) throws Exception {

        client = new Client(slsUrl, accessKeyId, accessKeySecret);
        int line = 100, page = 0, count = 0;

        if (!fileName.endsWith(".xls")) {
            fileName += ".xls";
        }

        long start = System.currentTimeMillis();
        try (FileOutputStream fos = new FileOutputStream(getFilePath(fileName));
             Workbook wb = new HSSFWorkbook();) {

            Sheet sheet = wb.createSheet();

            while (true) {

                int offset = page++ * line;

                GetLogsResponse response = query(startFromStr, toFromStr, keyWord, line, offset);
                if (response == null || response.GetLogs() == null || response.GetLogs().isEmpty()) {
                    break;
                }

                for (QueriedLog log : response.GetLogs()) {
                    JSONObject dataJson = convertJson(log);
                    // 留出一行作为列说明
                    Row row = sheet.createRow(++count);
                    handleLog(dataJson, row);
                }
            }

            wb.write(fos);

            System.out.println("************************************");
            System.out.println("write to excel done. count:" + count
                    + " use time:" + (System.currentTimeMillis() - start));
            System.out.println("************************************");
        }

    }

    /**
     * 查询日志并写入文本文件
     * @param fileName
     * @param startFromStr
     * @param toFromStr
     * @param keyWord
     * @throws Exception
     */
    protected void queryAndW2File(String fileName,
                               String startFromStr, String toFromStr,
                               String keyWord) throws Exception {

        client = new Client(slsUrl, accessKeyId, accessKeySecret);
        int line = 100, page = 0, count = 0;

        long start = System.currentTimeMillis();

        try (BufferedWriter bw = getBw(fileName)) {

            while (true) {

                int offset = page++ * line;

                GetLogsResponse response = query(startFromStr, toFromStr, keyWord, line, offset);
                if (response == null || response.GetLogs() == null || response.GetLogs().isEmpty()) {
                    break;
                }

                for (QueriedLog log : response.GetLogs()) {
                    JSONObject dataJson = convertJson(log);
                    String data = handleLog(dataJson);
                    if (data != null && !"".equals(data)) {
                        bw.write(data);
                        bw.write("\r\n");
                        count++;
                    }
                }
            }
        }

        System.out.println("************************************");
        System.out.println("write to txt done. count:" + count
                + " use time:" + (System.currentTimeMillis() - start));
        System.out.println("************************************");
    }

    protected GetLogsResponse query(
            String startFromStr, String toFromStr, String keyWord,
            int line, int offset) throws Exception {

        int startFrom = getTime(startFromStr);
        int toFrom = getTime(toFromStr);

        return client.GetLogs(project, logStore, startFrom, toFrom, topic, keyWord, line, offset, reverse);
    }

    /**
     * 获取写入文件流
     * @param fileName
     * @return
     * @throws IOException
     */
    protected BufferedWriter getBw(String fileName) throws IOException {

        if (bw != null) {
            return bw;
        }
        if (!fileName.endsWith(".txt")) {
            fileName += ".txt";
        }
        File f = new File(getFilePath(fileName));
        f.createNewFile();

        bw = new BufferedWriter(new FileWriter(f));
        return bw;
    }

    protected JSONObject convertJson(QueriedLog queriedLog) {

        JSONObject jsonObject = new JSONObject();

        for (LogContent logContent:queriedLog.mLogItem.mContents) {

            // json工具 无法解析的字段
            if (CONTENT_KEY_CONVERT.containsKey(logContent.mKey)) {
                jsonObject.put(CONTENT_KEY_CONVERT.get(logContent.mKey), logContent.mValue);
                continue;
            }

            // 扩展字段
            if (EXTEND_MAP.equals(logContent.mKey)) {
                JSONObject jsonMap = JSONObject.parseObject(logContent.mValue);
                jsonObject.put(logContent.mKey, jsonMap);
                continue;
            }

            jsonObject.put(logContent.mKey, logContent.mValue);
        }
        return jsonObject;
    }

    protected String getFilePath(String fileName) {
        return System.getProperty("user.dir") + "/" + fileName;
    }

    /**
     * 时间字符串 转 秒
     * @param dataStr
     * @return
     * @throws ParseException
     */
    private int getTime(String dataStr) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATA_FORMAT);
        Date date = simpleDateFormat.parse(dataStr);
        // sls 时间范围单位为秒
        return (int) (date.getTime() / 1000);
    }

}
