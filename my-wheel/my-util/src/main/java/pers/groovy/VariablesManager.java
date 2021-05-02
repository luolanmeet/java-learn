package pers.groovy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 变量管理
 * @auther ken.ck
 * @date 2021/4/30 10:40
 */
public class VariablesManager {

    public Map<Integer, Set<String>> variables = new HashMap<>();

    public String registerVariables(GroovyBuilder builder, String variablesName, Integer level,
                                  String parentField, String fieldType, Integer groovyLevel) {

        Set<String> sets = variables.computeIfAbsent(level, k -> new HashSet<>());
        String fieldName = parentField + "_" + variablesName;

        if (sets.contains(variablesName)) {
            return fieldName;
        }

        sets.add(variablesName);

        if (fieldType.equals(GroovyConstant.OBJECT)) {
            builder.appendWithSpaceEnter("def " + fieldName + " = [:]", groovyLevel);
        } else {
            builder.appendWithSpaceEnter("def " + fieldName + " = []", groovyLevel);
        }

        builder.appendWithSpaceEnter(parentField + ".put(\"" + variablesName + "\", " + fieldName + ")",  groovyLevel);

        return fieldName;
    }

}
