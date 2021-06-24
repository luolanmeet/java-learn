package pers.flow.content;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 上下文对象
 * @auther ken.ck
 * @date 2021/6/24 07:45
 */
@Data
public class FlowContent {

    private Map<String, String> params = new HashMap<>();

    public String getParam(String key) {
        return params.get(key);
    }

}
