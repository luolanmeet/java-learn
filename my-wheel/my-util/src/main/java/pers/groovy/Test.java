package pers.groovy;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther ken.ck
 * @date 2021/5/2 15:28
 */
public class Test {

    public static void main(String[] args) {

        // json -> json
        GroovyBuilder builder = GroovyBuilder.builder();
        List<String> mapperStrs = new ArrayList<>();

        mapperStrs.add("id:int:baseinfo.id1:int;");
        mapperStrs.add("name:string:baseinfo.name1:string;");
        mapperStrs.add("friend.id:int:friend1.id:int;");
        mapperStrs.add("friend.name:string:friend1.name:string;");
        mapperStrs.add("hobby:stringArray:baseinfo.hobby1:stringArray;");

        VariablesManager variablesManager = new VariablesManager();
        Mapper mapper = new Mapper("jsonObject", FieldType.OBJECT, variablesManager);

        for (String mapperStr : mapperStrs) {
            mapper.addChild(mapperStr);
        }

        builder.appendWithSpaceEnter("", 0);
        mapper.buildScript(builder, "jsonObject", "target", 1);
        System.out.println(builder.build());
    }

}
