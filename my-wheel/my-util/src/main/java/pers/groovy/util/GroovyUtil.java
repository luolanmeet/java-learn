package pers.groovy.util;

import pers.groovy.constant.FieldType;
import pers.groovy.constant.OperateType;

/**
 * 工具类
 * @auther ken.ck
 * @date 2021/5/2 15:11
 */
public class GroovyUtil {

    private static final String ARRAY = "Array";

    public static void checkOperate(String[] operateSentence) {

        // 检验操作类型是否支持
        if (!OperateType.SUPPORT_OPERATE.contains(operateSentence[0])) {
            throw new RuntimeException("not support operate:" + operateSentence[0]);
        }

        // 检验操作入参
        if (operateSentence.length < 2 && !OperateType.WITHOUT_PARAM_OPERATE.contains(operateSentence[0])) {
            throw new RuntimeException("operate:" + operateSentence[0] + " need a param");
        }

    }

    /**
     * 填充非空判断语句
     * @param groovyBuilder
     * @param originField
     * @param level
     */
    public static void appendNotNull(GroovyBuilder groovyBuilder, String originField, int level) {
        groovyBuilder
                .appendWithSpaceEnter("if (!" + originField + ") {", level)
                    .appendWithSpaceEnter("map.put(\"error\", \"" + originField + "为空\")", level + 1)
                    .appendWithSpaceEnter("return map", level + 1)
                .appendWithSpaceEnter("}", level);
    }

    /**
     * 是否为数组类型
     * @param fieldType
     * @return
     */
    public static boolean isArray(String fieldType) {

        if (fieldType == null || fieldType.isEmpty()) {
            return false;
        }

        return fieldType.endsWith(ARRAY);
    }

    /**
     * 是否为基本数据类
     */
    public static boolean isBaseTypeArray(String fieldType) {

        if (fieldType == null || fieldType.isEmpty()) {
            return false;
        }

        return !fieldType.equals(FieldType.OBJECT_ARRAY);
    }

    /**
     * 做类型转换
     * @param originField
     * @return
     */
    public static String caseType(String originFieldType, String targetFieldType, String originField) {

        if (originFieldType.equals(targetFieldType)) {
            return originField;
        }

        if (FieldType.STRING.equals(originFieldType) || FieldType.STRING_ARRAY.equals(originFieldType)) {
            switch (targetFieldType) {
                case FieldType.INT:
                case FieldType.INT_ARRAY:
                    return originField + "?.toInteger()";
                case FieldType.FLOAT:
                case FieldType.FLOAT_ARRAY:
                    return originField + "?.toFloat()";
                default:
                    return originField;
            }
        }

        if (FieldType.INT.equals(originFieldType) || FieldType.INT_ARRAY.equals(originFieldType)) {
            switch (targetFieldType) {
                case FieldType.STRING:
                case FieldType.STRING_ARRAY:
                    return originField + "?.toString()";
                case FieldType.FLOAT:
                case FieldType.FLOAT_ARRAY:
                    return originField + "?.toFloat()";
                default:
                    return originField;
            }
        }

        if (FieldType.FLOAT.equals(originFieldType) || FieldType.FLOAT_ARRAY.equals(originFieldType)) {
            switch (targetFieldType) {
                case FieldType.INT:
                case FieldType.INT_ARRAY:
                    return originField + "?.toInteger()";
                case FieldType.STRING:
                case FieldType.STRING_ARRAY:
                    return originField + "?.toString()";
                default:
                    return originField;
            }
        }

        return originField;
    }

}
