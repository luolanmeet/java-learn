package pers.groovy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
     * 注册变量类型，只注册对象和数组类型
     * @param path
     * @param type
     */
    public void registerVariablesType(String path, String type) {
        if (FieldType.OBJECT.equals(type) || GroovyUtil.isArray(type)) {
            variablesTypeMap.put(path, type);
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
