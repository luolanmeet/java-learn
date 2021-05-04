package pers.groovy.constant;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * 操作类型
 * @auther ken.ck
 * @date 2021/5/2 15:05
 */
public class OperateType {

    /*
     *************************
     *        操作常量         *
     *************************
     */

    /**
     * 非空判断
     */
    public static final String NOT_NULL = "notNull";
    /**
     * 类型转换
     */
    public static final String CHANGE_TYPE = "changeType";
    /**
     * 乘
     */
    public static final String MULTIPLY = "multiply";
    /**
     * 除
     */
    public static final String DIVIDE = "divide";
    /**
     * 加
     */
    public static final String PLUS = "plus";
    /**
     * 减
     */
    public static final String REDUCE = "reduce";
    /**
     * 日期格式转换
     */
    public static final String DATA_FORMAT = "dataFormat";
    /**
     * 填充默认值
     */
    public static final String DEFAULT_VAL = "defaultVal";

    /**
     * 支持的操作
     */
    public static final Set<String> SUPPORT_OPERATE = new HashSet<String>() {
        {
            for (Field field : OperateType.class.getDeclaredFields()) {
                try {
                    if (String.class.equals(field.getType())) {
                        add(field.get(OperateType.class).toString());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     * 不需要参数的操作
     */
    public static final Set<String> WITHOUT_PARAM_OPERATE = new HashSet<String>() {
        {
            add(NOT_NULL);
        }
    };

}
