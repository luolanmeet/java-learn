package pers.format;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author cck
 */
public class JsonFormatProcessor implements FormatProcessor {
    
    @Override
    public <T> String format(T obj) {
        return "JsonFormatProcessor: " + JSON.toJSONString(obj);
    }
}
