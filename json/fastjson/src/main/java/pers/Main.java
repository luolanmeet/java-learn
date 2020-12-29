package pers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther ken.ck
 * @date 2020/12/29 23:21
 */
public class Main {

    public String str = "HelloWorld";

    public static void main(String[] args) {

        List<Main> list = new ArrayList<>();
        Main obj = new Main();
        list.add(obj);
        list.add(obj);

        System.out.println(JSON.toJSONString(list));

        // 禁止FastJSON对循环/重复引用的处理
        System.out.println(JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect));
    }

}
