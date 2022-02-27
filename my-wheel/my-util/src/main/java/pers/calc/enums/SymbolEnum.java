package pers.calc.enums;

/**
 * 符号枚举
 * @auther ken.ck
 * @date 2021/11/16 10:27
 */
public enum SymbolEnum {

    ADD("+", "相加/拼接", 10, OpsTypeEnum.OP),
    SUBTRACT("-", "相减", 10, OpsTypeEnum.OP),
    MULTIPLY("*", "相乘", 11, OpsTypeEnum.OP),
    DIVIDED("/", "相除", 11, OpsTypeEnum.OP),
    MOLD("%", "取模", 11, OpsTypeEnum.OP),

    EMPTY("(", "空实现", 0, OpsTypeEnum.FUNC),
    SUB_STRING("subString", "切割字符串", 2, OpsTypeEnum.FUNC),
    TO_INT("toInt", "数据转整型", 0, OpsTypeEnum.FUNC),

    LOOP_ADD("loopAdd", "循环相加", 0, OpsTypeEnum.LOOP_FUNC),
    ;

    /**
     * 符号
     */
    private String symbol;
    /**
     * 描述
     */
    private String desc;
    /**
     * 操作符：优先级
     * 函数：参数数量
     */
    private Integer num;
    /**
     * 类型
     */
    private OpsTypeEnum type;

    SymbolEnum(String symbol, String desc, Integer num, OpsTypeEnum type) {
        this.symbol = symbol;
        this.desc = desc;
        this.num = num;
        this.type = type;
    }

    public String getSymbol() {
        return symbol;
    }

    public Integer getNum() {
        return num;
    }

    public OpsTypeEnum getType() {
        return type;
    }

}
