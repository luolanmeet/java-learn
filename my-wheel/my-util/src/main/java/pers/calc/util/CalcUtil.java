package pers.calc.util;

import pers.calc.enums.ParamTypeEnum;
import pers.calc.enums.SymbolEnum;
import pers.calc.param.Param;
import pers.calc.param.SourceParam;

import java.util.Deque;

/**
 * 运算工具
 * @auther ken.ck
 * @date 2021/11/18 14:55
 */
public class CalcUtil {

    /**
     * 转为整型
     * @param param
     * @return
     */
    public static Param toInt(Param param) {

        if (param.getType() == ParamTypeEnum.INT_CONTANT) {
            return param;
        }

        return new Param(
                ParamTypeEnum.INT_CONTANT,
                Integer.valueOf(param.getString()));
    }

    /**
     * 字符串截取
     * @param param
     * @param start
     * @param end
     * @return
     */
    public static Param subString(Param param, Param start, Param end) {

        String str = param.getString();
        int startIdx = start.getInteger();
        int endIdx = end.getInteger();

        return new Param(ParamTypeEnum.STRING_CONTANT, str.substring(startIdx, endIdx));
    }

    /**
     * 计算
     * @param params
     * @param symbols
     * @param sourceParam
     */
    public static void calc(Deque<Param> params, Deque<SymbolEnum> symbols, SourceParam sourceParam) {

        // p2 p1 顺序不能换
        Param p2 = params.pollLast();
        Param p1 = params.pollLast();

        p1 = translateParam(p1, sourceParam);
        p2 = translateParam(p2, sourceParam);

        Param res = null;
        SymbolEnum symbol = symbols.pollLast();

        switch (symbol) {
            case ADD: res = add(p1, p2); break;
            case SUBTRACT: res = subtract(p1, p2); break;
            case MULTIPLY: res = multiply(p1, p2); break;
            case DIVIDED: res = divided(p1, p2); break;
            case MOLD: res = mold(p1, p2); break;
            default:
                break;
        }

        params.addLast(res);
    }

    public static Param add(Param p1, Param p2) {

        // 整数相加
        if (p1.getType() == ParamTypeEnum.INT_CONTANT && p2.getType() == ParamTypeEnum.INT_CONTANT) {
            int resValue = p1.getInteger() + p2.getInteger();
            return new Param(ParamTypeEnum.INT_CONTANT, resValue);
        }

        // 字符串拼接
        String resValue = p1.getString() + p2.getString();
        return new Param(ParamTypeEnum.STRING_CONTANT, resValue);
    }

    public static Param subtract(Param p1, Param p2) {

        int resValue = p1.getInteger() - p2.getInteger();
        return new Param(ParamTypeEnum.INT_CONTANT, resValue);
    }

    public static Param multiply(Param p1, Param p2) {

        int resValue = p1.getInteger() * p2.getInteger();
        return new Param(ParamTypeEnum.INT_CONTANT, resValue);
    }

    public static Param divided(Param p1, Param p2) {

        int resValue = p1.getInteger() / p2.getInteger();
        return new Param(ParamTypeEnum.INT_CONTANT, resValue);
    }

    public static Param mold(Param p1, Param p2) {

        int resValue = p1.getInteger() % p2.getInteger();
        return new Param(ParamTypeEnum.INT_CONTANT, resValue);
    }

    /**
     * 转换参数，变量 -> 常量
     * @param param
     * @param sourceParam
     * @return
     */
    public static Param translateParam(Param param, SourceParam sourceParam) {

        // 常量参数，直接使用即可
        if (param.getType() != ParamTypeEnum.VARIABLES) {
            return param;
        }

        // 字段路径
        String paramPath = param.getString();

        return sourceParam.getParam(paramPath);
    }

}
