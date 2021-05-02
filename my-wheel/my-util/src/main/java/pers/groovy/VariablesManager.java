package pers.groovy;

import java.util.*;

/**
 * 变量管理
 * @auther ken.ck
 * @date 2021/4/30 10:40
 */
public class VariablesManager {

    /**
     * key : path
     * value : type
     */
    public Map<String, String> variablesTypeMap = new HashMap<>();

    /**
     * key : path
     * value : define name
     */
    public Map<String, String> variablesNameMap = new HashMap<>();

    /**
     * 原数据数组变量编号
     */
    public int originArrayVariablesNo = 1;

    /**
     * 目标数据数组变量编号
     */
    public int targetArrayVariablesNo = 1000;

    public int getOriginArrayVariablesNo() {
        return originArrayVariablesNo++;
    }

    public int getTargetArrayVariablesNo() {
        return targetArrayVariablesNo++;
    }

    /**
     * 注册变量类型，只注册对象和数组类型
     * @param path
     * @param type
     */
    public void registerVariablesType(String path, String type) {

        // 直接指定变量类型的优先级最高
        if (FieldType.OBJECT.equals(type) || GroovyUtil.isArray(type)) {
            variablesTypeMap.put(path, type);
            return ;
        }

        // 默认变量类型：除最后一个路径值，其余字段类型默认为对象
        String[] fields = path.split(GroovyConstant.POINT_SPLIT);
        StringJoiner tmpFieldPath = new StringJoiner(".");

        for (int i = 0; i < fields.length - 1; i++) {
            tmpFieldPath.add(fields[i]);
            if (!variablesTypeMap.containsKey(tmpFieldPath.toString())) {
                variablesTypeMap.put(tmpFieldPath.toString(), FieldType.OBJECT);
            }
        }
    }

    /**
     * 声明变量，只声明对象和数组类型的变量
     * @param builder
     * @param variablesPath
     * @param parentField
     * @param variablesName
     * @param level
     * @return
     */
    public String registerVariables(GroovyBuilder builder, String variablesPath,
                                    String parentField, String variablesName, Integer level) {

        if (variablesNameMap.containsKey(variablesPath)) {
            return variablesNameMap.get(variablesPath);
        }

        String fieldName = parentField + "_" + variablesName;
        String fieldType = variablesTypeMap.get(variablesPath);

        if (fieldType == null) {
            return parentField;
        }

        if (fieldType.equals(FieldType.OBJECT)) {
            builder.appendWithSpaceEnter("def " + fieldName + " = [:]", level);
        } else {
            builder.appendWithSpaceEnter("def " + fieldName + " = []", level);
        }

        builder.appendWithSpaceEnter(parentField + ".put(\"" + variablesName + "\", " + fieldName + ")",  level);

        variablesNameMap.put(variablesPath, fieldName);
        return fieldName;
    }

}
