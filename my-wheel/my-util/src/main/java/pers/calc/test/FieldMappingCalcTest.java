package pers.calc.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import pers.calc.FieldMappingCalc;
import pers.calc.enums.ParamTypeEnum;
import pers.calc.param.JsonSourceParam;
import pers.calc.param.Param;
import pers.calc.param.SourceParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther ken.ck
 * @date 2021/11/18 14:44
 */
public class FieldMappingCalcTest {

    FieldMappingCalc calc = new FieldMappingCalc();

    @Test
    public void test1() {
        JSONObject jsonObject = JSON.parseObject("{\"order\":{\"price\":120,\"orderCode\":\"BP123456\"}}");
        Map<String, ParamTypeEnum> paramTypeMap = new HashMap<>();
        paramTypeMap.put("order.price", ParamTypeEnum.INT_CONTANT);
        paramTypeMap.put("order.orderCode", ParamTypeEnum.STRING_CONTANT);
        SourceParam sourceParam = new JsonSourceParam(jsonObject, paramTypeMap);

        String s = "1+2*3+$order.orderCode+\"hello world\"";
        Param result = calc.calculate(s, sourceParam);
        Assert.assertEquals(result.getType(), ParamTypeEnum.STRING_CONTANT);
        Assert.assertEquals(result.getString(), "7BP123456hello world");
    }

    @Test
    public void test2() {
        JSONObject jsonObject = JSON.parseObject("{\"order\":{\"price\":120,\"orderCode\":\"BP123456\"}}");
        Map<String, ParamTypeEnum> paramTypeMap = new HashMap<>();
        paramTypeMap.put("order.price", ParamTypeEnum.INT_CONTANT);
        paramTypeMap.put("order.orderCode", ParamTypeEnum.STRING_CONTANT);
        SourceParam sourceParam = new JsonSourceParam(jsonObject, paramTypeMap);

        String s = "14/2+2*3+10%3+\"hello world\"+2*5+$order.orderCode";
        Param result = calc.calculate(s, sourceParam);
        Assert.assertEquals(result.getType(), ParamTypeEnum.STRING_CONTANT);
        Assert.assertEquals(result.getString(), "14hello world10BP123456");
    }

    @Test
    public void test3() {
        JSONObject jsonObject = JSON.parseObject("{\"order\":{\"price\":120,\"orderCode\":\"BP123456\"}}");
        Map<String, ParamTypeEnum> paramTypeMap = new HashMap<>();
        paramTypeMap.put("order.price", ParamTypeEnum.INT_CONTANT);
        paramTypeMap.put("order.orderCode", ParamTypeEnum.STRING_CONTANT);
        SourceParam sourceParam = new JsonSourceParam(jsonObject, paramTypeMap);

        String s = "1+2*3+$order.price";
        Param result = calc.calculate(s, sourceParam);
        Assert.assertEquals(result.getType(), ParamTypeEnum.INT_CONTANT);
        Assert.assertEquals(result.getInteger().intValue(), 127);
    }

    @Test
    public void test4() {
        JSONObject jsonObject = JSON.parseObject("{\"order\":{\"price\":120,\"orderCode\":\"BP123456\"}}");
        Map<String, ParamTypeEnum> paramTypeMap = new HashMap<>();
        paramTypeMap.put("order.price", ParamTypeEnum.INT_CONTANT);
        paramTypeMap.put("order.orderCode", ParamTypeEnum.STRING_CONTANT);
        SourceParam sourceParam = new JsonSourceParam(jsonObject, paramTypeMap);

        String s = "(1+2)*(5-(10/5))+((2+\"   \")+11111)+$order.orderCode+$order.price";
        Param result = calc.calculate(s, sourceParam);
        Assert.assertEquals(result.getType(), ParamTypeEnum.STRING_CONTANT);
        Assert.assertEquals(result.getString(), "92   11111BP123456120");
    }

    @Test
    public void test5() {
        JSONObject jsonObject = JSON.parseObject("{\"order\":{\"price\":120,\"orderCode\":\"BP123456\"}}");
        Map<String, ParamTypeEnum> paramTypeMap = new HashMap<>();
        paramTypeMap.put("order.price", ParamTypeEnum.INT_CONTANT);
        paramTypeMap.put("order.orderCode", ParamTypeEnum.STRING_CONTANT);
        SourceParam sourceParam = new JsonSourceParam(jsonObject, paramTypeMap);

        String s = "toInt(\"1\")+2";
        Param result = calc.calculate(s, sourceParam);
        Assert.assertEquals(result.getType(), ParamTypeEnum.INT_CONTANT);
        Assert.assertEquals(result.getInteger().intValue(), 3);
    }

    @Test
    public void test6() {
        JSONObject jsonObject = JSON.parseObject("{\"order\":{\"price\":120,\"orderCode\":\"BP123456\"}}");
        Map<String, ParamTypeEnum> paramTypeMap = new HashMap<>();
        paramTypeMap.put("order.price", ParamTypeEnum.INT_CONTANT);
        paramTypeMap.put("order.orderCode", ParamTypeEnum.STRING_CONTANT);
        SourceParam sourceParam = new JsonSourceParam(jsonObject, paramTypeMap);

        String s = "subString(\"abcdefg\",0,3)";
        Param result = calc.calculate(s, sourceParam);
        Assert.assertEquals(result.getType(), ParamTypeEnum.STRING_CONTANT);
        Assert.assertEquals(result.getString(), "abc");
    }

    @Test
    public void testLoopAdd() {
        JSONObject jsonObject = JSON.parseObject("{\"orderCode\":\"BP123456\",\"orderLine\":[{\"sku\":[{\"num\":1,\"price\":9},{\"num\":2,\"price\":5}]},{\"sku\":[{\"num\":1,\"price\":12},{\"num\":3,\"price\":1}]}]}");
        Map<String, ParamTypeEnum> paramTypeMap = new HashMap<>();
        paramTypeMap.put("orderCode", ParamTypeEnum.STRING_CONTANT);
        paramTypeMap.put("orderLine", ParamTypeEnum.OBJ_LIST);
        paramTypeMap.put("orderLine.sku", ParamTypeEnum.OBJ_LIST);
        paramTypeMap.put("orderLine.sku.price", ParamTypeEnum.INT_CONTANT);
        paramTypeMap.put("orderLine.sku.num", ParamTypeEnum.INT_CONTANT);
        SourceParam sourceParam = new JsonSourceParam(jsonObject, paramTypeMap);

        String s = "loopAdd($orderLine.sku.price*$orderLine.sku.num)";
        Param result = calc.calculate(s, sourceParam);
    }

}
