package pers.calc.util;

import org.apache.commons.lang3.tuple.ImmutablePair;
import pers.calc.enums.ParamTypeEnum;
import pers.calc.enums.SymbolEnum;
import pers.calc.param.Param;

import java.util.*;

/**
 * 解析工具
 * @auther ken.ck
 * @date 2021/11/16 10:07
 */
public class ParseUtil {

    private static int SYMBOL_MAX_LEN = 0;
    private static final Map<String, SymbolEnum> SYMBOL_MAP = new HashMap<>();
    static {
        Arrays.stream(SymbolEnum.values()).forEach(
                symbolEnum -> {
                    SYMBOL_MAX_LEN = Math.max(symbolEnum.getSymbol().length(), SYMBOL_MAX_LEN);
                    SYMBOL_MAP.put(symbolEnum.getSymbol(), symbolEnum);
                });
    }

    /**
     * 获取符号
     * @param idx
     * @param formula
     * @return <长度，操作符>
     */
    public static ImmutablePair<Integer, SymbolEnum> getSymbol(int idx, String formula) {
        String symbol = "";
        SymbolEnum res = null;
        while (idx < formula.length() && symbol.length() <= SYMBOL_MAX_LEN) {
            symbol += formula.charAt(idx++);
            res = SYMBOL_MAP.getOrDefault(symbol, res);
        }

        return res == null ? null : ImmutablePair.of(res.getSymbol().length(), res);
    }

    /**
     * 获取参数
     * @param idx
     * @param formula
     * @return
     */
    public static ImmutablePair<Integer, Param> getParam(int idx, String formula) {

        // 字符串参数
        if (formula.charAt(idx) == '\"') {
            StringBuilder value = new StringBuilder();
            // 删除第一个 "
            idx++;
            while (idx < formula.length() && '\"' != formula.charAt(idx)) {
                value.append(formula.charAt(idx));
                idx++;
            }
            return ImmutablePair.of(
                    value.length() + 2,
                    new Param(ParamTypeEnum.STRING_CONTANT, value.toString()));
        }

        // 数字参数
        if (Character.isDigit(formula.charAt(idx))) {
            int value = 0;
            while (idx < formula.length() && Character.isDigit((formula.charAt(idx)))) {
                value = value * 10 + (formula.charAt(idx) - '0');
                idx++;
            }
            return ImmutablePair.of(
                    (value + "").length(),
                    new Param(ParamTypeEnum.INT_CONTANT, value));
        }

        // 变量
        if (formula.charAt(idx) == '$') {
            idx++;
            StringBuilder value = new StringBuilder();
            while (idx < formula.length() && !isSymbol(formula.charAt(idx))) {
                value.append(formula.charAt(idx));
                idx++;
            }
            return ImmutablePair.of(
                    value.length() + 1,
                    new Param(ParamTypeEnum.VARIABLES, value.toString()));
        }

        throw new RuntimeException("can't parse param. " + idx + " " + formula);
    }

    /**
     * 是否为运算符
     * @param symbol
     * @return
     */
    public static boolean isSymbol(char symbol) {
        return ')' == symbol || SYMBOL_MAP.containsKey("" + symbol);
    }

    /**
     * 获取函数的参数
     * @param funcFormula
     * @param num
     * @return
     */
    public static ImmutablePair<Integer, List<Param>> getFuncParam(String funcFormula, Integer num) {

        // 参数所占的字符长度
        int len = 0;
        // 参数以 , 分隔
        int right = funcFormula.length() - 1;

        Param[] params = new Param[num];

        // 从后往前解析出参数，最外层的函数的参数一定是在最右边
        for (int i = num - 1; i >= 0; i--) {

            // 字符串参数
            if (funcFormula.charAt(right) == '\"') {
                StringBuilder value = new StringBuilder();
                // 删除 "
                right--;
                while(right > 1) {
                    if (funcFormula.charAt(right) == '"' && funcFormula.charAt(right - 1) == ',') {
                        // 删除 ,"
                        right -= 2;
                        break;
                    }
                    value.insert(0, funcFormula.charAt(right));
                    right--;
                }
                // ,"xxx"  ->  参数长度
                len += value.length() + 3;
                params[i] = new Param(ParamTypeEnum.STRING_CONTANT, value.toString());
                continue;
            }

            // 整型参数
            if (Character.isDigit(funcFormula.charAt(right))) {
                int value = 0;
                int tmp = 1;
                while(right >= 0 && funcFormula.charAt(right) != ',') {
                    value = value + (funcFormula.charAt(right) - '0') * tmp;
                    tmp *= 10;
                    right--;
                }
                // 删除 ,
                right--;
                // ,xxx  ->  参数长度
                len += (value + "").length() + 1;
                params[i] = new Param(ParamTypeEnum.INT_CONTANT, value);
                continue;
            }

            // 变量参数
            StringBuilder value = new StringBuilder();
            while(right > 1) {
                if (funcFormula.charAt(right) == '$' && funcFormula.charAt(right - 1) == ',') {
                    // 删除 ,$
                    right -= 2;
                    break;
                }
                value.insert(0, funcFormula.charAt(right));
                right--;
            }
            // ,$xxx  ->  参数长度
            len += value.length() + 2;
            params[i] = new Param(ParamTypeEnum.VARIABLES, value.toString());
        }

        List<Param> paramList = Arrays.asList(params);
        return ImmutablePair.of(len, paramList);
    }

    /**
     * 获取算式中所有变量
     * @param funcFormula
     * @return
     */
    public static List<Param> getAllVariable(String funcFormula) {

        List<Param> params = new ArrayList<>();

        int idx = 0;
        while (idx < funcFormula.length()) {
            if (funcFormula.charAt(idx) != '$') {
                idx++;
                continue;
            }
            ImmutablePair<Integer, Param> paramPair = getParam(idx, funcFormula);
            params.add(paramPair.right);
            idx += paramPair.left;
        }

        return params;
    }
}
