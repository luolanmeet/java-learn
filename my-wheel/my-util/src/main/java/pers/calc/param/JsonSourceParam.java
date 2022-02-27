package pers.calc.param;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import pers.calc.enums.ParamTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * json格式的源数据
 * @auther ken.ck
 * @date 2021/11/19 14:54
 */
public class JsonSourceParam extends SourceParam {

    /**
     * 源数据
     */
    private final JSONObject jsonObject;
    /**
     * 数据类型
     */
    private final Map<String, ParamTypeEnum> paramTypeMap;
    /**
     * 暂存集合变量
     */
    private final Map<String, JSONArray> tmpJsonArrayMap;

    public JsonSourceParam(JSONObject jsonObject, Map<String, ParamTypeEnum> paramTypeMap) {
        this.jsonObject = jsonObject;
        this.paramTypeMap = paramTypeMap;
        this.tmpJsonArrayMap = new HashMap<>();
    }

    @Override
    public Param getParam(String paramPath) {

        JSONObject tmp = jsonObject;

        String[] fields = paramPath.split("\\.");
        for (int i = 0; i < fields.length - 1; i++) {
            tmp = tmp.getJSONObject(fields[i]);
        }

        ParamTypeEnum paramTypeEnum = paramTypeMap.get(paramPath);
        return getParam(paramTypeEnum, tmp, fields[fields.length - 1]);
    }

    @Override
    public String getListVariablePath(String paramPath) {

        // 基本类型数组
        ParamTypeEnum paramTypeEnum = paramTypeMap.get(paramPath);
        if (paramTypeEnum == ParamTypeEnum.BASE_LIST) {
            return paramPath;
        }

        // 对象数组
        paramPath = paramPath.substring(0, paramPath.lastIndexOf("."));
        paramTypeEnum = paramTypeMap.get(paramPath);
        return paramTypeEnum == ParamTypeEnum.OBJ_LIST ? paramPath : null;
    }

    @Override
    public boolean isListVariable(String paramPath) {
        ParamTypeEnum paramTypeEnum = paramTypeMap.get(paramPath);
        return paramTypeEnum == ParamTypeEnum.OBJ_LIST || paramTypeEnum == ParamTypeEnum.BASE_LIST;
    }

    @Override
    public boolean hasNext(String listVariablePath) {

        String tmpPath = listVariablePath;




//        paramPath = paramPath.substring(0, paramPath.lastIndexOf("."));

        JSONArray array = tmpJsonArrayMap.get(listVariablePath);
        if (array == null) {
            return false;
        }

        Integer idx = variableReaderIndexMap.get(listVariablePath);

        return false;
    }

    @Override
    public void increateReaderIndex(String listVariablePath) {

        JSONArray array = tmpJsonArrayMap.get(listVariablePath);
        if (array == null) {
            return ;
        }

        Integer indx = variableReaderIndexMap.get(listVariablePath);
    }

    private Param getParam(ParamTypeEnum paramTypeEnum, JSONObject jsonObject, String key) {

        switch (paramTypeEnum) {
            case INT_CONTANT:
                return new Param(paramTypeEnum, jsonObject.getInteger(key));
            case STRING_CONTANT:
                return new Param(paramTypeEnum, jsonObject.getString(key));
            default:
                throw new RuntimeException("获取参数失败. " + key + " " + paramTypeEnum);
        }
    }

}
