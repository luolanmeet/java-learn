package pers.groovy.constant;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * 字段类型
 * @auther ken.ck
 * @date 2021/5/2 14:40
 */
public class FieldType {

    /*
     *************************
     *     基本数据类型        *
     *************************
     */
    /**
     * 字符串
     */
    public static final String STRING = "string";

    /**
     * 整形
     */
    public static final String LONG = "long";

    /**
     * 浮点型
     */
    public static final String FLOAT = "float";

    /*
     *************************
     *     对象及数组          *
     *************************
     */
    /**
     * 对象
     */
    public static final String OBJECT = "object";

    /**
     * 浮点型数组
     */
    public static final String FLOAT_ARRAY = "floatArray";

    /**
     * 整形数组
     */
    public static final String LONG_ARRAY = "longArray";

    /**
     * 字符串数组
     */
    public static final String STRING_ARRAY = "stringArray";

    /**
     * 对象数组
     */
    public static final String OBJECT_ARRAY = "objectArray";

    /**
     * 支持的类型
     */
    public static final Set<String> SUPPORT_TYPE = new HashSet<String>() {
        {
            for (Field field : FieldType.class.getDeclaredFields()) {
                try {
                    if (String.class.equals(field.getType())) {
                        add(field.get(FieldType.class).toString());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    };

}
