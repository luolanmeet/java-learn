package pers.calc.enums;

/**
 * 参数类型
 * @auther ken.ck
 * @date 2021/11/17 17:08
 */
public enum ParamTypeEnum {

    OBJ("对象"),
    VARIABLES("变量"),
    STRING_CONTANT("字符串常量"),
    INT_CONTANT("整型常量"),
    OBJ_LIST("对象集合"),
    BASE_LIST("基本类型集合"),
    ;

    private String desc;

    ParamTypeEnum(String desc) {
        this.desc = desc;
    }

}
