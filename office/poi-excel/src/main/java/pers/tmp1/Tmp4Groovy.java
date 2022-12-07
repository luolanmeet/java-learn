package pers.tmp1;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @auther ken.ck
 * @date 2022/1/7 11:10
 */
public class Tmp4Groovy {

    static class Solution {
        String id;
        String name;
        Map<Integer, String> groovy;
    }

    public static void main(String[] args) throws IOException {

        InputStream is = new FileInputStream("/Users/chenken/Downloads/执行结果1.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        List<Solution> solutionList = new ArrayList<>();
        int count = 0;
        // 遍历所有行
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            XSSFRow row = sheet.getRow(i);

            // 方案id， 方案名称， 脚本id， 脚本名称
            Solution solution = new Solution();
            solutionList.add(solution);

            solution.id = row.getCell(0).getStringCellValue();
            solution.name = row.getCell(1).getStringCellValue();
            solution.groovy = getGroovy(row.getCell(2).getStringCellValue());
            count += solution.groovy.size();
        }

        // 重复的脚本
        Map<Integer, String> repeatGroovy = getRepeatGroovy(solutionList);
        // 不重复的脚本
        Map<Integer, String> noRepeatGroovy = getNotRepeatGroovy(solutionList, repeatGroovy);

        // 输出表格
        String title = "<h1>方案脚本(" + count + ")</h1>";
        System.out.println(title);
        System.out.println(createTable(solutionList, repeatGroovy.keySet()));

        title = "<h1>可能的个性化脚本(" + noRepeatGroovy.size() + ")</h1>";
        System.out.println(title);
        System.out.println(createGroovyTable(noRepeatGroovy));

        title = "<h1>可能的通用脚本(" + repeatGroovy.size() + ")</h1>";
        System.out.println(title);
        System.out.println(createGroovyTable(repeatGroovy));
    }

    private static Map<Integer, String> getNotRepeatGroovy(List<Solution> solutionList, Map<Integer, String> repeatGroovy) {

        Map<Integer, String> result = new HashMap<>(64);
        for (Solution solution : solutionList) {
            Map<Integer, String> map = solution.groovy;
            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                if (repeatGroovy.containsKey(entry.getKey())) {
                    continue;
                }
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    private static StringBuilder createGroovyTable(Map<Integer, String> repeatGroovy) {

        StringBuilder table = new StringBuilder();
        table.append("<table border=\"1\">");
        table.append("<tr>");
        table.append("<td>脚本id</td><td>脚本名称</td>");
        table.append("</tr>");

        for (Map.Entry<Integer, String> entry : repeatGroovy.entrySet()) {
            table.append("<tr>");
            table.append("<td>").append(entry.getKey()).append("</td>");
            table.append("<td>").append(entry.getValue()).append("</td>");
            table.append("</tr>");
        }

        table.append("</table>");
        return table;
    }

    private static StringBuilder createTable(List<Solution> solutionList, Set<Integer> repeatIds) {

        StringBuilder table = new StringBuilder();
        table.append("<table border=\"1\">");
        table.append("<tr>");
        table.append("<td>方案id</td><td>方案名称</td><td>脚本id</td><td>脚本名称</td>");
        table.append("</tr>");

        for (Solution solution : solutionList) {

            if (solution.groovy.size() <= 0) {
                continue;
            }

            List<String> groovyTdStr = getGroovyTableStr(solution.groovy, repeatIds);

            if (groovyTdStr.size() == 1) {
                table.append("<tr>");
                table.append("<td>").append(solution.id).append("</td>");
                table.append("<td>").append(solution.name).append("</br>").append(groovyTdStr.size()).append("</td>");
                table.append(groovyTdStr.get(0));
                table.append("</tr>");
                continue;
            }

            table.append("<tr>");
            table.append("<td").append(" rowspan=\"").append(groovyTdStr.size()).append("\">").append(solution.id).append("</td>");
            table.append("<td").append(" rowspan=\"").append(groovyTdStr.size()).append("\">").append(solution.name).append("</br>").append(groovyTdStr.size()).append("</td>");
            table.append(groovyTdStr.get(0));
            table.append("</tr>");

            for (int i = 1; i < groovyTdStr.size(); i++) {
                table.append("<tr>");
                table.append(groovyTdStr.get(i));
                table.append("</tr>");
            }
        }

        table.append("</table>");

        return table;
    }

    private static Map<Integer, String> getRepeatGroovy(List<Solution> solutionList) {
        Map<Integer, String> result = new HashMap<>(64);
        Set<Integer> tmp = new HashSet<>();
        for (Solution solution : solutionList) {
            Map<Integer, String> map = solution.groovy;
            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                if (tmp.contains(entry.getKey())) {
                    result.put(entry.getKey(), entry.getValue());
                }
                tmp.add(entry.getKey());
            }
        }
        return result;
    }

    private static List<String> getGroovyTableStr(Map<Integer, String> groovy, Set<Integer> repeatIds) {

        List<String> result = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : groovy.entrySet()) {
            StringBuilder tmp = new StringBuilder();

            if (repeatIds.contains(entry.getKey())) {
                tmp.append("<td bgcolor=\"red\">").append(entry.getKey()).append(" 重复").append("</td>");
                tmp.append("<td bgcolor=\"red\">").append(entry.getValue()).append("</td>");
            } else {
                tmp.append("<td>").append(entry.getKey()).append("</td>");
                tmp.append("<td>").append(entry.getValue()).append("</td>");
            }

            result.add(tmp.toString());
        }
        return result;
    }

    private static Map<Integer, String> getGroovy(String jsonData) {

        Map<Integer, String> groovy = new HashMap<>(32);

        JSONArray jsonArray = JSON.parseArray(jsonData);
        for (int i = 0; i < jsonArray.size(); i++) {

            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (!"pluginDataChange".equals(jsonObject.getString("type"))) {
                continue;
            }

            groovy.put(jsonObject.getInteger("pluginNameKey"), jsonObject.getString("text"));
        }

        return groovy;
    }

}
