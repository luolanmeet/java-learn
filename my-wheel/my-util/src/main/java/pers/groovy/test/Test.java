package pers.groovy.test;

import pers.groovy.constant.FieldType;
import pers.groovy.mapper.ObjectMapper;
import pers.groovy.util.GroovyBuilder;
import pers.groovy.util.GroovyUtil;
import pers.groovy.util.VariablesManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @auther ken.ck
 * @date 2021/5/2 15:28
 */
public class Test {

    public static void main(String[] args) {

        String [] mapperStrs = {
                "HEADER.PLANT:string:deliveryOrder.warehouseCode:string;",
                "ZMM_RESERVATION_ITEMS:objectArray:orderLines:objectArray;",
                "ZMM_RESERVATION_ITEMS.WEMPF:string:orderLines.areaCode:string;",
                "ZMM_RESERVATION_ITEMS.PLANT:string:deliveryOrder.warehouseCode:string;",
        };

        VariablesManager variablesManager = new VariablesManager();
        ObjectMapper objectMapper = new ObjectMapper("", "jsonObject", FieldType.OBJECT, variablesManager);
        objectMapper.setFieldPath("");

        for (String mapperStr : mapperStrs) {
            objectMapper.addMapper(mapperStr);
        }

        variablesManager.buildEarlyBuildVariablesPathMap();

        // json -> json
        GroovyBuilder builder = GroovyBuilder.builder();

        // 声明日期转换类变量
        if (!variablesManager.dateFormatVariablesNameMap.isEmpty()) {
            builder.importSimpleDateFormat().start().appendWithEnter("");
            for (Map.Entry<String, String> dateFormatVariables : variablesManager.dateFormatVariablesNameMap.entrySet()) {
                GroovyUtil.defDateFormatVariables(dateFormatVariables.getKey(), dateFormatVariables.getValue(), builder, 1);
            }
        } else {
            builder.start();
        }

        builder.appendWithSpaceEnter("", 0);
        objectMapper.generateScript(builder, "jsonObject", "target", 1);
        System.out.println(builder.build());
    }

}
