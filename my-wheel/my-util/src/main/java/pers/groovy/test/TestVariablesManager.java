package pers.groovy.test;

import pers.groovy.constant.GroovyConstant;
import pers.groovy.mapper.FieldMapper;
import pers.groovy.util.VariablesManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @auther ken.ck
 * @date 2021/5/10 23:04
 */
public class TestVariablesManager {


    public static void main(String[] args) {

        VariablesManager variablesManager = new VariablesManager();

        List<String> mapperStrs = new ArrayList<>();

//        mapperStrs.add("HEADER.PLANT:string:deliveryOrder.warehouseCode:string;");
//
//        mapperStrs.add("ZMM_RESERVATION_ITEMS:objectArray:orderLines:objectArray;");
//        mapperStrs.add("ZMM_RESERVATION_ITEMS.WEMPF:string:orderLines.extendProps.areaCode:string;");
//        mapperStrs.add("ZMM_RESERVATION_ITEMS.PLANT:string:deliveryOrder.warehouseCode:string;");
//        mapperStrs.add("ZMM_RESERVATION_ITEMS.OBJ.PLANTA:string:deliveryOrder.warehouseCode:string;");

        mapperStrs.add("a.aa.aaa:string:deliveryOrder.c1:string;");
        mapperStrs.add("a.aa.aaa2:string:delivery.c:string;");

        mapperStrs.add("b.bb.bbb:string:deliveryOrder.c2:string;");
        mapperStrs.add("b.bb.bbb2:string:deliveryOrder2.c2:string;");
        mapperStrs.add("c.bb.bbb2:string:deliveryOrder2.c3:string;");

        for (String mapperStr : mapperStrs) {

            FieldMapper fieldMapper = FieldMapper.getSimpleMapper(mapperStr, variablesManager);

            // 注册需要声明的变量
            variablesManager.registerVariablesType(
                    fieldMapper.getOriginFieldPath(), fieldMapper.getOriginFieldType(),
                    fieldMapper.getTargetFieldPath(), fieldMapper.getTargetFieldType());
        }

        Map<String, Set<String>> earlyVariablesMap = variablesManager.earlyVariablesMap;
        System.out.println();

        variablesManager.buildEarlyVariablesPathMap();
        System.out.println();
    }

}
