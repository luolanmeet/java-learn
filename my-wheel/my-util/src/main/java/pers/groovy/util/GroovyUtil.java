package pers.groovy.util;

import pers.groovy.constant.FieldType;
import pers.groovy.constant.GroovyConstant;
import pers.groovy.constant.OperateType;
import pers.groovy.mapper.Operate;

import java.util.List;
import java.util.StringJoiner;

/**
 * 工具类
 * @auther ken.ck
 * @date 2021/5/2 15:11
 */
public class GroovyUtil {

    private static final String ARRAY = "Array";

    /**
     * 执行操作
     *
     * @param variablesManager
     * @param operates
     * @param originField
     * @return
     */
    public static String executeOperate(
            VariablesManager variablesManager, List<Operate> operates,
            String originFieldType, String originField) {

        boolean hasPlusOrReduce = false;

        for (Operate operate : operates) {

            switch (operate.getOperateType()) {
                case OperateType.CHANGE_TYPE:
                    originField = changeType(originFieldType, operate.getOperateVal(), originField);
                    originFieldType = operate.getOperateVal();
                    break;
                case OperateType.DATA_FORMAT:
                    String dateFormatVariables = variablesManager.dateFormatVariablesNameMap.get(operate.getOperateVal());
                    originField = dateFormatVariables + ".format(" + originField + ")";
                    break;
                case OperateType.MULTIPLY:
                    if (hasPlusOrReduce) {
                        originField = "(" + originField + ") * " + operate.getOperateVal();
                    } else {
                        originField += " * " + operate.getOperateVal();
                    }
                    hasPlusOrReduce = false;
                    break;
                case OperateType.DIVIDE:
                    if (hasPlusOrReduce) {
                        originField = "(" + originField + ") / " + operate.getOperateVal();
                    } else {
                        originField += " / " + operate.getOperateVal();
                    }
                    hasPlusOrReduce = false;
                    break;
                case OperateType.PLUS:
                    originField += " + " + operate.getOperateVal();
                    hasPlusOrReduce = true;
                    break;
                case OperateType.REDUCE:
                    originField += " - " + operate.getOperateVal();
                    hasPlusOrReduce = true;
                    break;
                default:
                    break;
            }
        }

        return originField;
    }

    /**
     * 声明日期转换变量
     * @param format
     * @param variablesName
     * @param builder
     * @param level
     */
    public static void defDateFormatVariables(String format, String variablesName, GroovyBuilder builder, int level) {
        builder.appendWithSpaceEnter("def " + variablesName + " = new SimpleDateFormat(\"" + format + "\")", level);
    }

    /**
     * 校验操作
     * @param operateSentence
     */
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
     * 是否为基本数据数组
     */
    public static boolean isBaseTypeArray(String fieldType) {

        if (fieldType == null || fieldType.isEmpty() || !isArray(fieldType)) {
            return false;
        }

        return !fieldType.equals(FieldType.OBJECT_ARRAY);
    }

    /**
     * 是否为对象数组
     */
    public static boolean isObjectTypeArray(String fieldType) {

        if (fieldType == null || fieldType.isEmpty() || !isArray(fieldType)) {
            return false;
        }

        return fieldType.equals(FieldType.OBJECT_ARRAY);
    }

    /**
     * 做类型转换
     * @param originField
     * @return
     */
    public static String changeType(String originFieldType, String targetFieldType, String originField) {

        if (originFieldType.equals(targetFieldType)) {
            return originField;
        }

        if (FieldType.STRING.equals(originFieldType) || FieldType.STRING_ARRAY.equals(originFieldType)) {
            switch (targetFieldType) {
                case FieldType.LONG:
                case FieldType.LONG_ARRAY:
                    return originField + "?.toLong()";
                case FieldType.FLOAT:
                case FieldType.FLOAT_ARRAY:
                    return originField + "?.toFloat()";
                default:
                    return originField;
            }
        }

        if (FieldType.LONG.equals(originFieldType) || FieldType.LONG_ARRAY.equals(originFieldType)) {
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
                case FieldType.LONG:
                case FieldType.LONG_ARRAY:
                    return originField + "?.toLong()";
                case FieldType.STRING:
                case FieldType.STRING_ARRAY:
                    return originField + "?.toString()";
                default:
                    return originField;
            }
        }

        return originField;
    }

    /**
     * 获取默认值
     * @param targetFieldType
     * @param defaultVla
     * @return
     */
    public static String getDefaultValue(String targetFieldType, String defaultVla) {

        switch (targetFieldType) {
            case FieldType.STRING:
                return "\"" + defaultVla + "\"";
            default:
                return defaultVla;
        }
    }

    public static String getNewPath(String path, int length) {
        return getNewPath(path.split(GroovyConstant.POINT_SPLIT), length);
    }

    public static String getNewPath(String[] fields, int length) {

        length = Math.min(fields.length, length);

        StringJoiner tmpJoiner = new StringJoiner(".");
        for (int i = 0; i < length; i++) {
            tmpJoiner.add(fields[i]);
        }
        return tmpJoiner.toString();
    }

}
