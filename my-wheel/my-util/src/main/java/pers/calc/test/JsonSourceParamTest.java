package pers.calc.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import pers.calc.enums.ParamTypeEnum;
import pers.calc.param.JsonSourceParam;
import pers.calc.param.SourceParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther ken.ck
 * @date 2021/11/26 17:08
 */
public class JsonSourceParamTest {

    @Test
    public void testMarkReaderIndex() {

        JSONObject jsonObject = JSON.parseObject("{\"school\":[{\"name\":\"hy大学\",\"association\":{\"name\":\"学生会\",\"department\":[{\"name\":\"新闻\"},{\"name\":\"技术部\"}]}}]}");
        Map<String, ParamTypeEnum> paramTypeMap = new HashMap<>();
        paramTypeMap.put("school", ParamTypeEnum.OBJ_LIST);
        paramTypeMap.put("school.name", ParamTypeEnum.STRING_CONTANT);
        paramTypeMap.put("school.association", ParamTypeEnum.OBJ);
        paramTypeMap.put("school.association.name", ParamTypeEnum.STRING_CONTANT);
        paramTypeMap.put("school.association.department", ParamTypeEnum.OBJ_LIST);
        paramTypeMap.put("school.association.department.name", ParamTypeEnum.STRING_CONTANT);

        SourceParam sourceParam = new JsonSourceParam(jsonObject, paramTypeMap);
        sourceParam.markReaderIndex("school.association.department");

    }

}
