package pers.demo3;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author cck
 */
public class Main {

    public static void main(String[] args) {

        // 国家
        JSONObject country = new JSONObject();
        country.put("id", 1);
        country.put("name", "中国");

        // 用户
        JSONObject user = new JSONObject();
        user.fluentPut("id", 1)
                .fluentPut("name", "cck")
                .fluentPut("sex", 1)
                .put("country", country);

        Map<String, Object> map = new HashMap<>();
        map.put("phone", 12339999);
        map.put("avatar", "cck.jpg");
        map.put("hobby", Arrays.asList("音乐", "跑步", "游戏"));

        user.putAll(map);

        JSONObject country2 = JSON.parseObject(country.toJSONString());

        System.out.println(user.containsValue("cck"));
        System.out.println(user.containsValue("游戏"));
        System.out.println(user.containsValue(country));
        System.out.println(user.containsValue(country2));

        System.out.println(user.toJSONString());
    }

}
