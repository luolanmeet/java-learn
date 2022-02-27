package pers.calc.test;


import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Assert;
import org.junit.Test;
import pers.calc.enums.ParamTypeEnum;
import pers.calc.enums.SymbolEnum;
import pers.calc.param.Param;
import pers.calc.util.ParseUtil;

import java.util.List;

/**
 * @auther ken.ck
 * @date 2021/11/16 11:40
 */
public class ParseUtilTest {

    @Test
    public void testGetFunParam() {

        ImmutablePair<Integer, List<Param>> result = null;
        String funcFormula = "";

        funcFormula = ",222233,\"hello world\"";
        result = ParseUtil.getFuncParam(funcFormula, 2);
        Assert.assertEquals(result.left.intValue(), 21);
        Assert.assertEquals(result.right.get(0).getType(), ParamTypeEnum.INT_CONTANT);
        Assert.assertEquals(result.right.get(0).getInteger().intValue(), 222233);
        Assert.assertEquals(result.right.get(1).getType(), ParamTypeEnum.STRING_CONTANT);
        Assert.assertEquals(result.right.get(1).getString(), "hello world");

        funcFormula = ",\"hello world\",12222";
        result = ParseUtil.getFuncParam(funcFormula, 2);
        Assert.assertEquals(result.left.intValue(), 20);
        Assert.assertEquals(result.right.get(0).getType(), ParamTypeEnum.STRING_CONTANT);
        Assert.assertEquals(result.right.get(0).getString(), "hello world");
        Assert.assertEquals(result.right.get(1).getType(), ParamTypeEnum.INT_CONTANT);
        Assert.assertEquals(result.right.get(1).getInteger().intValue(), 12222);

        funcFormula = ",&orderLine.orderCode,\"hello world\",12222";
        result = ParseUtil.getFuncParam(funcFormula, 3);
        Assert.assertEquals(result.left.intValue(), 41);
        Assert.assertEquals(result.right.get(0).getType(), ParamTypeEnum.VARIABLES);
        Assert.assertEquals(result.right.get(0).getString(), "orderLine.orderCode");
        Assert.assertEquals(result.right.get(1).getType(), ParamTypeEnum.STRING_CONTANT);
        Assert.assertEquals(result.right.get(1).getString(), "hello world");
        Assert.assertEquals(result.right.get(2).getType(), ParamTypeEnum.INT_CONTANT);
        Assert.assertEquals(result.right.get(2).getInteger().intValue(), 12222);
    }

    @Test
    public void testGetParam() {

        ImmutablePair<Integer, Param> result = null;

        result = ParseUtil.getParam(
                0, "$orderLines.price*$orderLines.price)+$freight");
        Assert.assertEquals(result.left.intValue(), 17);
        Assert.assertEquals(result.right.getType(), ParamTypeEnum.VARIABLES);
        Assert.assertEquals(result.right.getString(), "orderLines.price");

        result = ParseUtil.getParam(0, "\"hello world\"+2222");
        Assert.assertEquals(result.left.intValue(), 13);
        Assert.assertEquals(result.right.getType(), ParamTypeEnum.STRING_CONTANT);
        Assert.assertEquals(result.right.getString(), "hello world");

        result = ParseUtil.getParam(0, "222223313+\"hello world\"");
        Assert.assertEquals(result.left.intValue(), 9);
        Assert.assertEquals(result.right.getType(), ParamTypeEnum.INT_CONTANT);
        Assert.assertEquals(result.right.getInteger().intValue(), 222223313);

        result = ParseUtil.getParam(10, "222223313+\"hello world\"");
        Assert.assertEquals(result.left.intValue(), 13);
        Assert.assertEquals(result.right.getType(), ParamTypeEnum.STRING_CONTANT);
        Assert.assertEquals(result.right.getString(), "hello world");
    }

    @Test
    public void testGetFunAndOp() {

        ImmutablePair<Integer, SymbolEnum> result = null;

        result = ParseUtil.getSymbol(
                0, "loopAdd(toInt($orderLines.price)*$orderLines.price)+$freight");
        Assert.assertEquals(result.left.intValue(), 7);
        Assert.assertEquals(result.right, SymbolEnum.LOOP_ADD);

        result = ParseUtil.getSymbol(8, "loopAdd(toInt($orderLines.price)*$orderLines.price)+$freight");
        Assert.assertEquals(result.left.intValue(), 5);
        Assert.assertEquals(result.right, SymbolEnum.TO_INT);

        result = ParseUtil.getSymbol(0, "(1+1)*2");
        Assert.assertEquals(result.left.intValue(), 1);
        Assert.assertEquals(result.right, SymbolEnum.EMPTY);
    }

    @Test
    public void testGetAllVariable() {

        String formula = "loopAdd($orderLine.sku.price*$orderLine.sku.num)";
        List<Param> result = ParseUtil.getAllVariable(formula);

        Assert.assertEquals(result.size(), 2);

        Assert.assertEquals(result.get(0).getType(), ParamTypeEnum.VARIABLES);
        Assert.assertEquals(result.get(0).getString(), "orderLine.sku.price");

        Assert.assertEquals(result.get(1).getType(), ParamTypeEnum.VARIABLES);
        Assert.assertEquals(result.get(1).getString(), "orderLine.sku.num");
    }

}
