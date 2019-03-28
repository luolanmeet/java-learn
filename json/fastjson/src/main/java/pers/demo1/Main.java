package pers.demo1;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Main {
    
    public static void main(String[] args) {
        
        User user = new User();
        user.setUserId(1);
        user.setName("cck");
        user.setHobby(Arrays.asList("看书", "编程", "游戏"));
        user.setBirthday(new Date());
        
        // Java 对象转 JSON 字符串 【toJSONString】
        String str = JSON.toJSONString(user);
        System.out.println(str);
        
        // JSON 字符串 转 Java 对象 【parseObject】
        User tempUser = JSON.parseObject(str, User.class);
        tempUser.setUserId(2);
        System.out.println(tempUser);
        
        // JSON 字符串 转 Java 对象 【parseObject】 不指定类型
        JSONObject jsonObject = JSON.parseObject(str);
        System.out.println(jsonObject.getInteger("id"));
        
        // Java 对象数组 转 JSON 字符串
        str = JSON.toJSONString(Arrays.asList(user, tempUser));
        System.out.println(str);
        
        // JSON 字符串 转 Java 对象数组 
        List<User> list = JSON.parseArray(str, User.class);
        System.out.println(list);
        
    }
    
}
