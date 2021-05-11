package pers.groovy.util;

import pers.groovy.constant.FieldType;
import pers.groovy.constant.GroovyConstant;

import java.util.*;

/**
 * 脚本变量管理类
 * @auther ken.ck
 * @date 2021/4/30 10:40
 */
public class VariablesManager {

    /**
     * 变量类型注册
     * key : path
     * value : type
     */
    public Map<String, String> variablesTypeMap = new HashMap<>();

    /**
     * 变量名注册
     * key : path
     * value : define name
     */
    public Map<String, String> variablesNameMap = new HashMap<>();

    /**
     * 日期格式化工具变量注册
     * key : 日期字符串格式
     * value : name
     */
    public Map<String, String> dateFormatVariablesNameMap = new HashMap<>();

    /**
     * 需要先创建的变量
     * key : 需要创建的变量的路径
     * value : 用到同个变量的原数据的路径
     */
    public Map<String, Set<String>> earlyVariablesMap = new HashMap<>();

    /**
     * 需要先创建的变量
     * key : 遍历至此路径时，需要声明value路径的变量
     * value : 需要创建的变量的路径
     */
    public Map<String, Set<String>> defEarlyVariablesPathMap = new HashMap<>();

    /**
     * 原数据数组变量编号
     */
    private int originArrayVariablesNo = 1;

    /**
     * 目标数据数组变量编号
     */
    private int targetArrayVariablesNo = 1000;

    /**
     * 日期变量编号
     */
    private int dataFormatVariablesNo = 1;

    public int getOriginArrayVariablesNo() {
        return originArrayVariablesNo++;
    }

    public int getTargetArrayVariablesNo() {
        return targetArrayVariablesNo++;
    }

    /**
     * 注册变量类型，只注册对象和数组类型
     * @param originFieldPath
     * @param originFieldType
     * @param targetFieldPath
     * @param targetFieldType
     */
    public void registerVariablesType(
            String originFieldPath, String originFieldType,
            String targetFieldPath, String targetFieldType) {

        // 当多个域使用到同一变量时，则该变量在进入某个域之前被创建
        registerEarlyVariables(originFieldPath, originFieldType, targetFieldPath, targetFieldType);

        // 直接指定变量类型的优先级最高
        if (FieldType.OBJECT.equals(targetFieldType) || GroovyUtil.isArray(targetFieldType)) {
            variablesTypeMap.put(targetFieldPath, targetFieldType);
            return ;
        }

        // 默认变量类型：除最后一个路径值，其余字段类型默认为对象
        String[] fields = targetFieldPath.split(GroovyConstant.POINT_SPLIT);
        StringJoiner tmpFieldPath = new StringJoiner(".");

        for (int i = 0; i < fields.length - 1; i++) {
            tmpFieldPath.add(fields[i]);
            if (!variablesTypeMap.containsKey(tmpFieldPath.toString())) {
                variablesTypeMap.put(tmpFieldPath.toString(), FieldType.OBJECT);
            }
        }

    }

    /**
     *
     * @param originFieldPath
     * @param originFieldType
     * @param targetFieldPath
     * @param targetFieldType
     */
    private void registerEarlyVariables(
            String originFieldPath, String originFieldType,
            String targetFieldPath, String targetFieldType) {

        // 不需要处理基础类型
        if (!FieldType.OBJECT.equals(originFieldType) && !GroovyUtil.isArray(originFieldType)) {
            String[] split = originFieldPath.split(GroovyConstant.POINT_SPLIT);
            // 只有一个字段且是基础类型，不需要处理
            if (split.length <= 1) {
                return ;
            }
            StringJoiner tmpJoiner = new StringJoiner(".");
            for (int i = 0; i < split.length - 1; i++) {
                tmpJoiner.add(split[i]);
            }
            originFieldPath = tmpJoiner.toString();
        }

        String[] targetFields = targetFieldPath.split(GroovyConstant.POINT_SPLIT);
        int targetFieldSize = targetFields.length;
        // 不需要处理基础类型
        if (!FieldType.OBJECT.equals(targetFieldType) && !GroovyUtil.isArray(targetFieldType)) {
            targetFieldSize--;
        }

        StringJoiner tmpTargetFieldPath = new StringJoiner(".");

        for (int j = 0; j < targetFieldSize; j++) {
            tmpTargetFieldPath.add(targetFields[j]);
            Set<String> paths = earlyVariablesMap.computeIfAbsent(tmpTargetFieldPath.toString(), t -> new HashSet<>());
            paths.add(originFieldPath);
        }

    }

    /**
     * 构建defEarlyVariablesPathMap
     */
    public void buildEarlyVariablesPathMap() {

        for (Map.Entry<String, Set<String>> entry : earlyVariablesMap.entrySet()) {

            int level = Integer.MAX_VALUE;

            Set<String> originPaths = entry.getValue();

            // 找到这个变量在哪个最小层级的域就被使用到
            for (String originPath : originPaths) {
                level = Integer.min(level, originPath.split(GroovyConstant.POINT_SPLIT).length);
            }

            // 把所有原数据路径都处理成同个层级数量
            Set<String> tmpOriginPaths = new HashSet<>();
            for (String originPath : originPaths) {
                String[] split = originPath.split(GroovyConstant.POINT_SPLIT);
                StringJoiner tmpOriginPath = new StringJoiner(".");
                for (int i = 0; i < level; i++) {
                    tmpOriginPath.add(split[i]);
                }
                tmpOriginPaths.add(tmpOriginPath.toString());
            }

            // 判断同个层级的路径是否在同个路径
            tmpOriginPaths = rebuildOriginPath(tmpOriginPaths, 0, level);

            String targetField = entry.getKey();
            for (String tmpOriginPath : tmpOriginPaths) {
                Set<String> variables = defEarlyVariablesPathMap.computeIfAbsent(tmpOriginPath, t -> new HashSet<>());
                variables.add(targetField);
            }
        }

    }

    /**
     * 多个原数据映射同一个目标数据变量，此处提取出共同的层级
     * @param originPaths
     * @param idx
     * @return
     */
    private Set<String> rebuildOriginPath(Set<String> originPaths, int idx, int level) {

        if (idx >= level || originPaths.size() == 1) {
            return originPaths;
        }

        String preOriginPath = null;
        boolean isFindLevel = false;

        for (String originPath : originPaths) {
            if (preOriginPath == null) {
                preOriginPath = originPath;
                continue;
            }

            if (!originPath.split(GroovyConstant.POINT_SPLIT)[idx].equals(preOriginPath.split(GroovyConstant.POINT_SPLIT)[idx])) {
                isFindLevel = true;
                break;
            }
        }

        if (isFindLevel) {
            Set<String> result = new HashSet<>();
            for (String originPath : originPaths) {
                String[] split = originPath.split(GroovyConstant.POINT_SPLIT);
                StringJoiner tmpOriginPath = new StringJoiner(".");
                for (int i = 0; i <= idx; i++) {
                    tmpOriginPath.add(split[i]);
                }
                result.add(tmpOriginPath.toString());
            }
            return result;
        }

        return rebuildOriginPath(originPaths, idx + 1, level);
    }

    /**
     * 声明变量，只声明对象和数组类型的变量
     * @param builder
     * @param variablesPath
     * @param parentField
     * @param parentFieldPath
     * @param variablesName
     * @param level
     * @return
     */
    public String registerVariables(GroovyBuilder builder, String variablesPath,
                                    String parentField, String parentFieldPath, String variablesName, Integer level) {

        /* 如果父类型是对象类型数组 */
        String parentFieldType = variablesTypeMap.get(parentFieldPath);
        if (GroovyUtil.isArray(parentFieldType) && !GroovyUtil.isBaseTypeArray(parentFieldType)) {

            // 避免循环遍历数组时，多次构建同一个目标对象
            String arrayVariablesName = parentFieldPath + "_";

            if (variablesNameMap.containsKey(arrayVariablesName)) {
                return variablesNameMap.get(arrayVariablesName);
            }

            String fieldName = parentField + "_" + getTargetArrayVariablesNo();
            String fieldType = variablesTypeMap.get(variablesPath);

            if (GroovyUtil.isArray(fieldType)) {
                builder.appendWithSpaceEnter("def " + fieldName + " = []", level);
            } else {
                builder.appendWithSpaceEnter("def " + fieldName + " = [:]", level);
            }

            builder.appendWithSpaceEnter(parentField + ".add(" + fieldName + ")", level);
            variablesNameMap.put(arrayVariablesName, fieldName);
            return fieldName;
        }

        /* 非对象数组类型 */

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

    /**
     * 注册日期转换变量
     * @param format
     */
    public void registerDateFormatVariables(String format) {
        String variablesName = dateFormatVariablesNameMap.get(format);
        if (variablesName == null) {
            variablesName = "dateFormat" + dataFormatVariablesNo++;
            dateFormatVariablesNameMap.put(format, variablesName);
        }
    }

}
