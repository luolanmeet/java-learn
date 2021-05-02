package pers.groovy;

import static pers.groovy.GroovyConstant.ENTER;
import static pers.groovy.GroovyConstant.SPACE4;

/**
 * @auther ken.ck
 * @date 2021/4/29 16:48
 */
public class GroovyBuilder {

    /**
     * 控制填充回车
     */
    boolean hasEnter = false;

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

}
