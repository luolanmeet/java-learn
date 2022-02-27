package pers.calc.param;

import pers.calc.enums.ParamTypeEnum;

/**
 * 运算参数
 * @auther ken.ck
 * @date 2021/11/17 17:07
 */
public class Param {

    /**
     * 参数类型
     */
    private ParamTypeEnum type;

    /**
     * 参数值
     */
    private Object value;

    public Param(ParamTypeEnum type, Object value) {
        this.type = type;
        this.value = value;
    }

    public ParamTypeEnum getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public String getString() {
        return this.value.toString();
    }

    /**
     * 强转为Integer类型
     * 注意！ type 是 {@link ParamTypeEnum.INT_CONTANT} 才可用此方法
     * @return
     */
    public Integer getInteger() {
        return (Integer) this.value;
    }

    @Override
    public String toString() {
        return "Param{" +
                "type=" + type +
                ", value=" + value +
                '}';
    }
}
