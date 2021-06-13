package pers.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.TreeMap;

/**
 * @auther ken.ck
 * @date 2021/6/14 01:28
 */
public class JsonUtil {

    /**
     * 排序json字段
     * @param jsonStr
     * @return
     */
    public static String sortField(String jsonStr) {
        // 默认就会按照字段排序
        // Feature.SortFeidFastMatch;
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        return jsonObject.toJSONString();
    }

    public static void main(String[] args) {

        String jsonStr = "{\"name\":\"BeJson\",\"url\":\"http://www.bejson.com\",\"page\":88,\"isNonProfit\":true,\"address\":{\"street\":\"科技园路.\",\"city\":\"江苏苏州\",\"country\":\"中国\"},\"links\":[{\"name\":\"Google\",\"url\":\"http://www.google.com\"},{\"name\":\"Baidu\",\"url\":\"http://www.baidu.com\"},{\"name\":\"SoSo\",\"url\":\"http://www.SoSo.com\"}]}";
        System.out.println(JsonUtil.sortField(jsonStr));
    }

}
