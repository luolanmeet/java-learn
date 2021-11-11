package pers.groovy;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @auther ken.ck
 * @date 2021/10/15 12:34
 */
public class CheckGroovyUtil {

    private static final Pattern PATTERN = Pattern.compile(".*=.*(\\[:\\]|\\[\\])");

    private static final String DEF = "def ";
    private static final String IGNORE_ONE = "//";
    private static final String IGNORE_TWO = "/*";

    public static List<String> check(String groovy) {
        String[] groovyLines = groovy.split("\n");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < groovyLines.length; i++) {
            if (checkLine(groovyLines[i])) {
                list.add("line:" + (i + 1) + " " + groovyLines[i]);
            }
        }

        return list;
    }

    private static boolean checkLine(String groovyLine) {

        groovyLine = groovyLine.trim();
        Matcher matcher = PATTERN.matcher(groovyLine);

        return !groovyLine.startsWith(DEF)
                && !groovyLine.startsWith(IGNORE_ONE)
                && !groovyLine.startsWith(IGNORE_TWO)
                && matcher.find();
    }

    public static void main(String[] args) {

        String groovy = "\"import com.fasterxml.jackson.databind.ObjectMapper\n" +
                "\n" +
                "/**\n" +
                " * 转换插件实现方法\n" +
                " * 编排引擎会调用 execute\n" +
                " */\n" +
                "def execute(v,f = 'JSON'){\n" +
                "    def map = ['value':'', 'format':'JSON','success':false,'error':'init']\n" +
                "\n" +
                "    // 解析xml\n" +
                "    def response = new XmlSlurper().parseText(v);\n" +
                "    def target = [:]\n" +
                "\n" +
                "    def flag = response.flag?.text()\n" +
                "    if (flag.equals('success')){\n" +
                "        target.put('success',true)\n" +
                "        target.put('errCode','')\n" +
                "        target.put('errDesc','')\n" +
                "    }else{\n" +
                "        target.put('success',false)\n" +
                "        target.put('errCode',response.code?.text())\n" +
                "        target.put('errDesc',response.message?.text())\n" +
                "    }\n" +
                "\n" +
                "    // 生成target报文\n" +
                "    ObjectMapper mapper = new ObjectMapper()\n" +
                "    map.put('value',mapper.writeValueAsString(target))\n" +
                "\n" +
                "    map.put('success',true)\n" +
                "    return map\n" +
                "}\"";

        List<String> checkResult = CheckGroovyUtil.check(groovy);
        checkResult.forEach(System.out::println);
    }

}
