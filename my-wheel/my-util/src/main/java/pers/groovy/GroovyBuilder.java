package pers.groovy;

import java.util.*;

import static pers.groovy.GroovyConstant.*;

/**
 * @auther ken.ck
 * @date 2021/4/29 16:48
 */
public class GroovyBuilder {

    private final StringBuilder groovy = new StringBuilder();

    public static GroovyBuilder builder() {
        GroovyBuilder groovyBuilder = new GroovyBuilder();
        groovyBuilder.appendWithEnter("import com.fasterxml.jackson.databind.ObjectMapper");
        groovyBuilder.appendWithEnter("import groovy.json.JsonSlurper").append(ENTER);
        groovyBuilder.appendWithEnter("def execute(v, f) {");
        groovyBuilder.appendWithSpaceEnter("def map = ['value': '', 'format': 'JSON', 'success': false, 'error': 'init']");
        groovyBuilder.appendWithSpaceEnter("def jsonObject = new JsonSlurper().parseText(v)");
        groovyBuilder.appendWithSpaceEnter("def target = [:]");
        return groovyBuilder;
    }

    public GroovyBuilder append(String str) {
        groovy.append(str);
        return this;
    }

    public GroovyBuilder appendWithEnter(String str) {
        groovy.append(str).append(ENTER);
        return this;
    }

    public GroovyBuilder appendWithSpaceEnter(String str) {
        return this.append(SPACE4).append(str).append(ENTER);
    }

    public GroovyBuilder appendWithSpaceEnter(String str, int level) {
        while (level-- > 0) {
            this.append(SPACE4);
        }
        return this.append(str).append(ENTER);
    }

    public String build() {
        this.appendWithSpaceEnter("ObjectMapper mapper = new ObjectMapper()")
                .appendWithSpaceEnter("map.put('value', mapper.writeValueAsString(target))")
                .appendWithSpaceEnter("map.put('success', true)")
                .appendWithSpaceEnter("return map")
                .append("}");
        return groovy.toString();
    }

    private static void parse(GroovyBuilder builder, List<String> mappers) {

        // 存储变量
        // 全局看变量是否存在
        Map<Integer, Set<String>> map = new HashMap<>();
        for (String mapper : mappers) {
            String[] sentences = mapper.split(";");
            parseMapper(builder, sentences, map);
        }
    }

    private static void parseMapper(GroovyBuilder builder, String[] sentences, Map<Integer, Set<String>> map) {

        // 映射声明
        String mapper = sentences[0];
        String[] fields = mapper.split(SENTENCE_INNER_SPLIT);

        // 操作声明
        Map<String, String> operateMap = new HashMap<>();
        if (sentences.length > 1) {
            for (int i = 1; i < sentences.length; i++) {
                String[] split = sentences[i].split(SENTENCE_INNER_SPLIT, 2);
                operateMap.put(split[0], split.length > 1 ? split[1] : null);
            }
        }

        int level = 1;

        // 判空操作
        if (operateMap.containsKey(NOT_NULL)) {
            // 拼接字段为 a?.b?.c
            StringJoiner fieldStr = new StringJoiner("?.");
            Arrays.stream(fields[0].split(POINT_SPLIT)).forEach(fieldStr::add);
            builder.appendWithSpaceEnter("if (!jsonObject." + fieldStr.toString() + ") {", level);
            builder.appendWithSpaceEnter("map.put(\"error\", \"" + fields[0] + "为空\")", level + 1);
            builder.appendWithSpaceEnter("return map", level + 1);
            builder.appendWithSpaceEnter("}", level);
        }

        // 值映射操作

    }

    public static void main(String[] args) {

        // json -> json
        GroovyBuilder builder = GroovyBuilder.builder();
        List<String> mapperStrs = new ArrayList<>();
//        parse(builder, mappers);

//        mapperStrs.add("id:string:tradeCode:string;notNull;");
//        mapperStrs.add("ss.id:string:tt.aaa.bbbb:string;");
//        mapperStrs.add("ss.id:string:tt.bbb:string;");
        mapperStrs.add("id:string:tt.ccc:string;");
        mapperStrs.add("yy:array:uu:array;");
//        mappers.add("baseInfo.payTime:timestamp:payInfo:string;dataFormat:yyyy-MM-dd HH:mm:ss;");

        VariablesManager variablesManager = new VariablesManager();
        Mapper mapper = new Mapper("jsonObject", OBJECT, variablesManager);

        for (String mapperStr : mapperStrs) {
            mapper.addChild(mapperStr);
        }

        builder.appendWithSpaceEnter("");
        mapper.buildScript(builder, "jsonObject", "target", 1);
        System.out.println(builder.build());
    }

}
