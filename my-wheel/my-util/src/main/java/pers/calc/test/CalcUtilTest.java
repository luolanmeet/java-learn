package pers.calc.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import pers.calc.enums.ParamTypeEnum;
import pers.calc.param.JsonSourceParam;
import pers.calc.param.Param;
import pers.calc.param.SourceParam;
import pers.calc.util.CalcUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther ken.ck
 * @date 2021/11/18 15:12
 */
public class CalcUtilTest {

    @Test
    public void testTranslateParam() {

        JSONObject jsonObject = JSON.parseObject("{\"order\":{\"price\":12,\"orderCode\":\"BP123456\"}}");
        Map<String, ParamTypeEnum> paramTypeMap = new HashMap<>();
        paramTypeMap.put("order.price", ParamTypeEnum.INT_CONTANT);
        paramTypeMap.put("order.orderCode", ParamTypeEnum.STRING_CONTANT);
        SourceParam sourceParam = new JsonSourceParam(jsonObject, paramTypeMap);

        Param p = new Param(ParamTypeEnum.VARIABLES, "order.orderCode");
        Param result = CalcUtil.translateParam(p, sourceParam);
        Assert.assertEquals(result.getType(), ParamTypeEnum.STRING_CONTANT);
        Assert.assertEquals(result.getString(), "BP123456");
    }

    @Test
    public void testAdd() {

        Param result = CalcUtil.add(
                new Param(ParamTypeEnum.INT_CONTANT, 1),
                new Param(ParamTypeEnum.INT_CONTANT, 2));
        Assert.assertEquals(result.getType(), ParamTypeEnum.INT_CONTANT);
        Assert.assertEquals(result.getInteger().intValue(), 3);

        result = CalcUtil.add(
                new Param(ParamTypeEnum.STRING_CONTANT, "321"),
                new Param(ParamTypeEnum.INT_CONTANT, 123));
        Assert.assertEquals(result.getType(), ParamTypeEnum.STRING_CONTANT);
        Assert.assertEquals(result.getString(), "321123");
    }

}
