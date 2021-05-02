package pers.groovy;

/**
 * @auther ken.ck
 * @date 2021/4/29 17:51
 */
public class GroovyConstant {

    static final String SPACE = " ";
    static final String SPACE4 = SPACE + SPACE + SPACE + SPACE;

    static final String ENTER = "\r\n";

    final static String SENTENCE_SPLIT = ";";
    final static String SENTENCE_INNER_SPLIT = ":";
    final static String POINT_SPLIT = "\\.";

    final static String VARIABLES_PRE_FIX = "target_";

    /*
     *************************
     *        字段类型         *
     *************************
     */
    /**
     * 对象
     */
    static final String OBJECT = "object";

    /**
     * 字符串
     */
    static final String STRING = "string";

    /**
     * 整形
     */
    static final String INT = "int";

    /**
     * 数组
     */
    static final String ARRAY = "array";

    /*
     *************************
     *        操作常量         *
     *************************
     */

    /**
     * 非空判断
     */
    static final String NOT_NULL = "notNull";
    /**
     * 日期格式转换
     */
    static final String DATA_FORMAT = "dataFormat";
    /**
     * 填充默认值
     */
    static final String DEFAULT_VAL = "defaultVal";

}