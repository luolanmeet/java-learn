package pers.calc.enums;

/**
 * 操作类型枚举
 * @auther ken.ck
 * @date 2021/11/16 15:14
 */
public enum OpsTypeEnum {

    OP("运算操作"),
    FUNC("函数"),
    LOOP_FUNC("循环函数"),
    ;

    private String desc;

    OpsTypeEnum(String desc) {
        this.desc = desc;
    }
}
