package pers.calc;

import org.apache.commons.lang3.tuple.ImmutablePair;
import pers.calc.enums.OpsTypeEnum;
import pers.calc.enums.SymbolEnum;
import pers.calc.param.Param;
import pers.calc.param.SourceParam;
import pers.calc.util.CalcUtil;
import pers.calc.util.ParseUtil;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 改造自 调度场算法
 * @auther ken.ck
 * @date 2021/11/15 17:05
 */
public class FieldMappingCalc {

    /**
     * 运行字段映射表达式
     * @param s
     * @param sourceParam
     * @return
     */
    public Param calculate(String s, SourceParam sourceParam) {

        // 存放所有的运算参数
        Deque<Param> params = new ArrayDeque<>();
        // 存放所有的运算符号
        Deque<SymbolEnum> symbols = new ArrayDeque<>();

        int idx = 0;

        while (idx < s.length()) {

            // 获取操作符
            ImmutablePair<Integer, SymbolEnum> symbolPair = ParseUtil.getSymbol(idx, s);

            if (symbolPair != null) {

                // 操作符
                if (symbolPair.right.getType() == OpsTypeEnum.OP) {

                    // 符号优先级支持
                    while (!symbols.isEmpty()) {
                        SymbolEnum prevSymbol = symbols.peekLast();
                        // 已经存在的运算符优先级 >= 将要入栈的运算符，则进行计算
                        if (prevSymbol.getNum() >= symbolPair.right.getNum()) {
                            CalcUtil.calc(params, symbols, sourceParam);
                            continue;
                        }
                        break;
                    }

                    symbols.addLast(symbolPair.right);
                    idx += symbolPair.left;
                    continue;
                }

                // 函数计算，函数运算会产生一个结果值，该值将作为参数入栈
                ImmutablePair<Integer, Param> funcResPair = executeFunc(symbolPair, idx, s, sourceParam);
                params.addLast(funcResPair.right);
                idx += funcResPair.left;
                continue;
            }

            // 获取操作数
            ImmutablePair<Integer, Param> paramPair = ParseUtil.getParam(idx, s);
            params.addLast(paramPair.right);
            idx += paramPair.left;
        }

        // 算完剩下的操作
        while (!symbols.isEmpty()) {
            CalcUtil.calc(params, symbols, sourceParam);
        }

        // 没有运算，只有取值的情况
        Param param = params.peekLast();
        return CalcUtil.translateParam(param, sourceParam);
    }

    /**
     * 执行函数
     * @param symbolPair
     * @param idx
     * @param s
     * @param sourceParam
     * @return
     */
    private ImmutablePair<Integer, Param> executeFunc(
            ImmutablePair<Integer, SymbolEnum> symbolPair,
            int idx, String s, SourceParam sourceParam) {

        SymbolEnum symbolEnum = symbolPair.getRight();

        // 找出函数的覆盖的式子范围
        int left = idx + symbolPair.left;
        int right = left;
        left += symbolEnum == SymbolEnum.EMPTY ? 0 : 1;

        int lCount = symbolEnum == SymbolEnum.EMPTY ? 1 : 0;
        int rCount = 0;

        while (right < s.length()) {
            if (s.charAt(right) == '(') {
                lCount++;
            } else if (s.charAt(right) == ')') {
                rCount++;
            }
            if (lCount != 0 && lCount == rCount) {
                break;
            }
            right++;
        }

        // 子算式
        String funcFormula = s.substring(left, right);
        // 子算式长度
        int funcLen = right - idx + 1;

        // 执行循环函数
        if (symbolEnum.getType() == OpsTypeEnum.LOOP_FUNC) {

            // 找出循环函数中循环的集合变量
            // 目前循环函数支持 处理同个集合内的数据的运算，即：loopAdd(list.x+list.y)
            // 不支持 不同集合内的数据的运算，即：loopAdd(list1.x+list2.y)
            List<Param> variables = ParseUtil.getAllVariable(funcFormula);
            String listVariablePath = null;
            for (Param variable : variables) {
                listVariablePath = sourceParam.getListVariablePath(variable.getString());
                if (listVariablePath != null) {
                    break;
                }
            }

            // 不是集合变量，则抛出异常
            if (listVariablePath == null) {
                throw new RuntimeException("循环函数无法处理非集合数据 ：" + funcFormula);
            }

            // 仅支持循环同个集合中的数据

            // 标记集合数据
            sourceParam.markReaderIndex(listVariablePath);

            List<Param> tmpResult = new ArrayList<>();
            // 遍历集合数据，执行函数
            while (sourceParam.hasNext(listVariablePath)) {
                // 执行函数，集合数据变为单值数据
                tmpResult.add(calculate(funcFormula, sourceParam));
                sourceParam.increateReaderIndex(listVariablePath);
            }

            // 清除集合数据标记
            sourceParam.resetReaderIndex(listVariablePath);

            // switch 执行循环函数的操作

            return null;
        }

        // 执行无参数的函数
        if (symbolEnum.getNum() == 0) {
            Param param = calculate(funcFormula, sourceParam);
            switch (symbolEnum) {
                case EMPTY:
                    return ImmutablePair.of(funcLen, param);
                case TO_INT:
                    return ImmutablePair.of(funcLen, CalcUtil.toInt(param));
                default:
                    throw new RuntimeException("未支持的函数：" + symbolEnum);
            }
        }

        // 执行有参数的函数

        // 获取函数的参数
        ImmutablePair<Integer, List<Param>> paramPair = ParseUtil.getFuncParam(funcFormula, symbolEnum.getNum());
        List<Param> params = paramPair.right;
        // 校验参数
        if (params.size() != symbolEnum.getNum()) {
            throw new RuntimeException("函数参数个数校验失败。式子：" + funcFormula);
        }

        funcFormula = funcFormula.substring(0, funcFormula.length() - paramPair.left);
        // 执行函数
        Param param = calculate(funcFormula, sourceParam);
        switch (symbolEnum) {
            case SUB_STRING:
                return ImmutablePair.of(funcLen, CalcUtil.subString(param, params.get(0), params.get(1)));
            default:
                throw new RuntimeException("未支持的函数：" + symbolEnum);
        }

    }

}
