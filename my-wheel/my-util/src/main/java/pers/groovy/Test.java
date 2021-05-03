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

        // 基本属性转对象
//        mapperStrs.add("id:int:baseinfo.id1:int;");
//        mapperStrs.add("name:string:baseinfo.name1:string;");
//        // 基本类型数组转基本类型数组
//        mapperStrs.add("hobby:stringArray:baseinfo.hobby1:stringArray;");
//
//        // 对象转对象
//        mapperStrs.add("bestFriend.id:int:bestFriend1.id:int;");
//        mapperStrs.add("bestFriend.name:string:bestFriend1.name:string;");
        // 对象中存在 对象数组
//        mapperStrs.add("bestFriend.childs:objectArray:bestFriend1.childs1:objectArray;");
//        mapperStrs.add("bestFriend.childs.age:int:bestFriend1.childs1.age:float;");
//        mapperStrs.add("bestFriend.childs.name:string:bestFriend1.childs1.name:string;");

        // 数组转对象
        mapperStrs.add("bestFriend.childs:objectArray:bestFriend1.childs:object;");
        mapperStrs.add("bestFriend.childs.age:int:bestFriend1.childs.age:int;");
        mapperStrs.add("bestFriend.childs.name:string:bestFriend1.childs.name:string;");
//
//        // 对象数组转对象数组
        mapperStrs.add("friends:objectArray:friends1:objectArray;");
        mapperStrs.add("friends.id:int:friends1.id:int;");
        mapperStrs.add("friends.name:string:friends1.name:string;");

//        mapperStrs.add("::baseinfos:objectArray;");
//        mapperStrs.add("id:int:baseinfos.id1:string;");
//        mapperStrs.add("name:string:baseinfos.name1:string;");

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
