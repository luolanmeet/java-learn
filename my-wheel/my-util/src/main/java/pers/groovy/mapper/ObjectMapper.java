package pers.groovy.mapper;

import pers.groovy.constant.FieldType;
import pers.groovy.constant.GroovyConstant;
import pers.groovy.util.GroovyBuilder;
import pers.groovy.util.GroovyUtil;
import pers.groovy.util.VariablesManager;

import java.util.*;

/**
 * 对象/数组类型 字段映射类
 * @auther ken.ck
 * @date 2021/4/29 20:13
 */
public class ObjectMapper {

    /**
     * 要解析的对象的字段名
     */
    private String fieldName;

    /**
     * 要解析的对象的类型
     */
    private String fieldType;

    /**
     * 要解析的对象的基本类型字段映射
     */
    private List<FieldMapper> fieldMappers;

    /**
     * 存储要解析的 对象/数组 的映射
     */
    private Map<String, FieldMapper> selfMapperMap;

    /**
     * 要解析的对象的对象/数组字段映射
     */
    private Map<String, ObjectMapper> childMapperMap;

    /**
     * 基本类型数组会有此变量
     */
    private VariablesManager variablesManager;

    public ObjectMapper(String fieldName, String fieldType, VariablesManager variablesManager) {

        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.variablesManager = variablesManager;

        this.childMapperMap = new HashMap<>();
        this.selfMapperMap = new HashMap<>();
        this.fieldMappers = new ArrayList<>();
    }

    public void addMapper(String mapperStr) {

        FieldMapper fieldMapper = FieldMapper.getSimpleMapper(mapperStr, variablesManager);
        String originFieldParentName = fieldMapper.getOriginFieldParentName();

        // 注册需要声明的变量
        variablesManager.registerVariablesType(fieldMapper.getTargetFieldPath(), fieldMapper.getTargetFieldType());

        // 只是做了变量声明
        if (fieldMapper.getOriginFieldPath() == null || fieldMapper.getOriginFieldPath().isEmpty()) {
            return ;
        }

        // 有父节点，表示未到最小解析字段
        if (originFieldParentName != null) {

            ObjectMapper objectMapper = childMapperMap.get(originFieldParentName);
            if (objectMapper == null) {
                // 默认父节点是对象类型
                objectMapper = new ObjectMapper(originFieldParentName, FieldType.OBJECT, variablesManager);
                childMapperMap.put(originFieldParentName, objectMapper);
            }
            objectMapper.addMapper(mapperStr.split(GroovyConstant.POINT_SPLIT, 2)[1]);
            return ;
        }

        // 无父节点

        // 基本数据类型映射
        if (!FieldType.OBJECT.equals(fieldMapper.getOriginFieldType())
                && !GroovyUtil.isArray(fieldMapper.getOriginFieldType())) {
            fieldMappers.add(fieldMapper);
            return ;
        }

        // 对象/数组类型映射
        ObjectMapper objectMapper = childMapperMap.get(fieldMapper.getOriginFieldPath());
        if (objectMapper == null) {
            objectMapper = new ObjectMapper(fieldMapper.getOriginFieldPath(), fieldMapper.getOriginFieldType(), variablesManager);
            childMapperMap.put(fieldMapper.getOriginFieldPath(), objectMapper);
        } else {
            // 更新类型
            objectMapper.setFieldType(fieldMapper.getOriginFieldType());
        }

        if (!objectMapper.selfMapperMap.containsKey(fieldMapper.getTargetFieldPath())) {
            objectMapper.selfMapperMap.put(fieldMapper.getTargetFieldPath(), fieldMapper);
        }
    }

    public void generateScript(
            GroovyBuilder builder, String originParentPath,
            String targetParentField, int level) {

        // 生成 普通类型字段 映射脚本
        for (FieldMapper fieldMapper : fieldMappers) {
            generateScript(builder, originParentPath, targetParentField, FieldType.OBJECT, level, fieldMapper);
        }

        // 生成 对象/数组类型字段 映射脚本
        for (Map.Entry<String, ObjectMapper> mapperEntry : childMapperMap.entrySet()) {

            builder.appendWithEnter("");

            ObjectMapper objectMapper = mapperEntry.getValue();

            String originFieldPath = originParentPath + "?." + objectMapper.getFieldName();

            // 处理 对象/数组 非空校验，暂存填充默认值操作
            List<FieldMapper> defaultValFieldMappers = new ArrayList<>();
            List<Operate> defaultValOperates = new ArrayList<>();
            if (objectMapper.selfMapperMap != null && !objectMapper.selfMapperMap.isEmpty()) {
                boolean hasNotNull = false;
                for (FieldMapper selfMapper : objectMapper.selfMapperMap.values()) {
                    // 非空操作
                    if (!hasNotNull && selfMapper.isNotNull()) {
                        GroovyUtil.appendNotNull(builder, originFieldPath, level);
                        hasNotNull = true;
                    }
                    if (GroovyUtil.isBaseTypeArray(objectMapper.fieldType)) {
                        selfMapper.setNotNull(false);
                    }
                    // 收集默认值操作
                    if (selfMapper.getDefaultValOperate() != null) {
                        defaultValFieldMappers.add(selfMapper);
                        defaultValOperates.add(selfMapper.getDefaultValOperate());
                    }
                }
            }

            // 是否有声明为空时设值的操作 -- 添加开始的判断
            if (!defaultValFieldMappers.isEmpty()) {
                builder.appendWithSpaceEnter("if (" + originFieldPath + ") {", level);
                level++;
            }

            // 处理对象类型字段 映射
            if (FieldType.OBJECT.equals(objectMapper.fieldType)) {
                objectMapper.generateScript(builder, originFieldPath, targetParentField, level);
            } else {
                // 处理数组类型字段 映射
                generateArrayScript(builder, originFieldPath, targetParentField, level, objectMapper);
            }

            // 是否有声明为空时设值的操作 -- 添加结束的设置默认值
            if (!defaultValFieldMappers.isEmpty()) {
                level--;
                builder.appendWithSpaceEnter("} else {", level);

                for (int i = 0 ; i < defaultValFieldMappers.size(); i++) {
                    // 设置默认值
                    String defaultValue = defaultValOperates.get(i).getOperateVal();
                    builder.appendWithSpaceEnter(targetParentField + ".put(\"" + defaultValFieldMappers.get(i).getTargetFieldName() + "\", " + defaultValue + ")", level + 1);
                }

                builder.appendWithSpaceEnter("}", level);
            }
        }
    }

    /**
     * 生成数组类型字段的脚本映射
     * @param builder
     * @param originFieldPath
     * @param targetParentField
     * @param level
     * @param objectMapper
     */
    private void generateArrayScript(
            GroovyBuilder builder, String originFieldPath, String targetParentField,
            int level, ObjectMapper objectMapper) {

        Collection<FieldMapper> selfMappers = objectMapper.selfMapperMap.values();

        // 先创建变量
        for (FieldMapper selfMapper : selfMappers) {
            buildVariables(builder, targetParentField, level, selfMapper);
        }

        // 遍历数组类型时的变量名
        String itemVariables = "item" + variablesManager.getOriginArrayVariablesNo();

        // 基础类型数组
        if (GroovyUtil.isBaseTypeArray(objectMapper.fieldType)) {
            // 数组
            builder.appendWithSpaceEnter("for (def "+ itemVariables + " : " + originFieldPath + ") {", level);

            for (FieldMapper selfMapper : selfMappers) {
                // 基本类型数组，当前遍历的对象即为目标数据
                selfMapper.setOriginFieldPath("");
                generateScript(builder, itemVariables, targetParentField, FieldType.OBJECT_ARRAY, level + 1, selfMapper);
            }

            builder.appendWithSpaceEnter("}", level);
        }

        // 对象数组
        if (GroovyUtil.isObjectTypeArray(objectMapper.fieldType)) {
            // 对象数组
            builder.appendWithSpaceEnter("for (def " + itemVariables + " : " + originFieldPath + ") {", level);
            objectMapper.generateScript(builder, itemVariables, targetParentField, level + 1);
            builder.appendWithSpaceEnter("}", level);
        }

    }

    private void generateScript(
            GroovyBuilder builder, String originParentPath,
            String targetParentField, String targetParentFieldType,
            int level, FieldMapper fieldMapper) {

        // 构造变量
        targetParentField = buildVariables(builder, targetParentField, level, fieldMapper);
        fieldMapper.generateScript(builder, variablesManager, originParentPath, targetParentField, targetParentFieldType, level);
    }

    private String buildVariables(GroovyBuilder builder, String targetParentField, int level, FieldMapper fieldMapper) {

        String targetVal = fieldMapper.getTargetFieldPath();
        String[] fields = targetVal.split(GroovyConstant.POINT_SPLIT);

        StringJoiner tmpFieldPath = new StringJoiner(".");
        StringJoiner tmpParentFieldPath = new StringJoiner(".");

        // 创建目标变量
        for (String field : fields) {
            tmpFieldPath.add(field);
            targetParentField = variablesManager.registerVariables(
                    builder, tmpFieldPath.toString(), targetParentField, tmpParentFieldPath.toString(), field, level);
            tmpParentFieldPath.add(field);
        }
        return targetParentField;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
}
