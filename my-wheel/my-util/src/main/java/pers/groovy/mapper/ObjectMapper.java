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
     * 存储要解析的对象的信息，主要为操作信息
     */
    private FieldMapper selfMapper;

    /**
     * 要解析的对象的对象/数组字段映射
     */
    private Map<String, ObjectMapper> childMapper;

    /**
     * 基本类型数组会有次变量
     */
    private VariablesManager variablesManager;

    public ObjectMapper(String fieldName, String fieldType, VariablesManager variablesManager) {

        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.variablesManager = variablesManager;

        this.childMapper = new HashMap<>();
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

            ObjectMapper objectMapper = childMapper.get(originFieldParentName);
            if (objectMapper == null) {
                // 默认父节点是对象类型
                objectMapper = new ObjectMapper(originFieldParentName, FieldType.OBJECT, variablesManager);
                childMapper.put(originFieldParentName, objectMapper);
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
        ObjectMapper objectMapper = childMapper.get(fieldMapper.getOriginFieldPath());
        if (objectMapper == null) {
            objectMapper = new ObjectMapper(fieldMapper.getOriginFieldPath(), fieldMapper.getOriginFieldType(), variablesManager);
            childMapper.put(fieldMapper.getOriginFieldPath(), objectMapper);
        } else {
            // 更新类型
            objectMapper.setFieldType(fieldMapper.getOriginFieldType());
        }

        fieldMapper.setOriginFieldPath("");
        objectMapper.selfMapper = fieldMapper;
    }

    public void generateScript(
            GroovyBuilder builder, String originParentPath,
            String targetParentField, int level) {

        // 生成 普通类型字段 映射脚本
        for (FieldMapper fieldMapper : fieldMappers) {
            generateScript(builder, originParentPath, targetParentField, FieldType.OBJECT, level, fieldMapper);
        }

        // 生成 对象/数组类型字段 映射脚本
        for (Map.Entry<String, ObjectMapper> mapperEntry : childMapper.entrySet()) {

            builder.appendWithEnter("");

            ObjectMapper objectMapper = mapperEntry.getValue();

            // 处理 对象/数组 非空校验
            if (objectMapper.selfMapper != null && objectMapper.selfMapper.isNotNull()) {
                GroovyUtil.appendNotNull(builder, originParentPath + "." + objectMapper.getFieldName(), level);
                if (GroovyUtil.isBaseTypeArray(objectMapper.fieldType)) {
                    objectMapper.selfMapper.setNotNull(false);
                }
            }

            String originFieldPath = originParentPath + "?." + objectMapper.getFieldName();

            // 是否有声明为空时设值的操作 -- 添加开始的判断
            Operate defaultValOperate = objectMapper.selfMapper.getDefaultValOperate();
            if (defaultValOperate != null) {
                objectMapper.selfMapper.setDefaultValOperate(null);
                builder.appendWithSpaceEnter("if (" + originFieldPath + ") {", level);
                level++;
            }

            // 处理对象类型字段 映射
            if (FieldType.OBJECT.equals(objectMapper.fieldType)) {
                objectMapper.generateScript(builder, originParentPath, targetParentField, level);
            } else {
                // 处理数组类型字段 映射
                generateArrayScript(builder, originFieldPath, targetParentField, level, objectMapper);
            }

            // 是否有声明为空时设值的操作 -- 添加结束的设置默认值
            if (defaultValOperate != null) {
                // 默认值
                String defaultValue = defaultValOperate.getOperateVal();

                level--;
                builder.appendWithSpaceEnter("} else {", level)
                        .appendWithSpaceEnter(targetParentField + ".put(\"" + objectMapper.selfMapper.getTargetFieldName() + "\", " + defaultValue + ")", level + 1)
                        .appendWithSpaceEnter("}", level);
            }

        }

    }

    private void generateArrayScript(GroovyBuilder builder, String originFieldPath, String targetParentField, int level, ObjectMapper objectMapper) {


        // 先创建变量
        buildVariables(builder, targetParentField, level, objectMapper.selfMapper);

        // 遍历数组类型时的变量名
        String itemVariables = "item" + variablesManager.getOriginArrayVariablesNo();

        // 基础类型数组
        if (GroovyUtil.isBaseTypeArray(objectMapper.fieldType)) {
            // 数组
            builder.appendWithSpaceEnter("for (def "+ itemVariables + " : " + originFieldPath + ") {", level);
            generateScript(builder, itemVariables, targetParentField, FieldType.OBJECT_ARRAY, level + 1, objectMapper.selfMapper);
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
