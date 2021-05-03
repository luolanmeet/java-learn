package pers.groovy.util;

import static pers.groovy.constant.GroovyConstant.ENTER;
import static pers.groovy.constant.GroovyConstant.SPACE4;

/**
 * 脚本构造类
 * @auther ken.ck
 * @date 2021/4/29 16:48
 */
public class GroovyBuilder {

    /**
     * 控制填充回车
     */
    boolean hasEnter = false;

    private final StringBuilder groovy = new StringBuilder();

    private GroovyBuilder() {}

    public static GroovyBuilder builder() {
        GroovyBuilder groovyBuilder = new GroovyBuilder();
        groovyBuilder.appendWithEnter("import com.fasterxml.jackson.databind.ObjectMapper")
                .appendWithEnter("import groovy.json.JsonSlurper").append(ENTER)
                .appendWithEnter("def execute(v, f) {").append(ENTER)
                .appendWithSpaceEnter("def map = ['value': '', 'format': 'JSON', 'success': false, 'error': 'init']")
                .appendWithSpaceEnter("def jsonObject = new JsonSlurper().parseText(v)")
                .appendWithSpaceEnter("def target = [:]");
        return groovyBuilder;
    }

    public GroovyBuilder append(String str) {
        groovy.append(str);
        return this;
    }

    public GroovyBuilder appendWithEnter(String str) {

        if (str == null || str.isEmpty()) {
            if (!hasEnter) {
                groovy.append(ENTER);
                hasEnter = true;
            }
            return this;
        }

        groovy.append(str).append(ENTER);
        hasEnter = false;
        return this;
    }

    public GroovyBuilder appendWithSpaceEnter(String str) {
        return this.append(SPACE4).append(str).append(ENTER);
    }

    public GroovyBuilder appendWithSpaceEnter(String str, int level) {

        if (str == null || str.isEmpty()) {
            if (!hasEnter) {
                groovy.append(ENTER);
                hasEnter = true;
            }
            return this;
        }

        while (level-- > 0) {
            this.append(SPACE4);
        }
        hasEnter = false;
        return this.append(str).append(ENTER);
    }

    public String build() {
        this.appendWithEnter("")
                .appendWithSpaceEnter("ObjectMapper mapper = new ObjectMapper()")
                .appendWithSpaceEnter("map.put('value', mapper.writeValueAsString(target))")
                .appendWithSpaceEnter("map.put('success', true)")
                .appendWithSpaceEnter("return map")
                .append("}");
        return groovy.toString();
    }

}
