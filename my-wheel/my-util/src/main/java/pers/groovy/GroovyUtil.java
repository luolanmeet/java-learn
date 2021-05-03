package pers.groovy;

/**
 * @auther ken.ck
 * @date 2021/5/2 15:11
 */
public class GroovyUtil {

    private static final String ARRAY = "Array";

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

        if (FieldType.STRING.equals(originFieldType)) {
            switch (targetFieldType) {
                case FieldType.INT:
                    return originField + "?.toInteger()";
                case FieldType.FLOAT:
                    return originField + "?.toFloat()";
                default:
                    return originField;
            }
        }

        if (FieldType.INT.equals(originFieldType)) {
            switch (targetFieldType) {
                case FieldType.STRING:
                    return originField + "?.toString()";
                case FieldType.FLOAT:
                    return originField + "?.toFloat()";
                default:
                    return originField;
            }
        }

        if (FieldType.FLOAT.equals(originFieldType)) {
            switch (targetFieldType) {
                case FieldType.INT:
                    return originField + "?.toInteger()";
                case FieldType.STRING:
                    return originField + "?.toString()";
                default:
                    return originField;
            }
        }

        return originField;
    }

}
