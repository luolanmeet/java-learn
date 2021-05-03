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
     * 用于辅助要解析的基础类型数组对象
     */
    private FieldMapper baseArrayFieldMapper;

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

        FieldMapper fieldMapper = FieldMapper.getSimpleMapper(mapperStr);
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
            fieldMapper.parseOperate();
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
        objectMapper.baseArrayFieldMapper = fieldMapper;

    }

    public void buildScript(GroovyBuilder builder, String originParentPath, String targetParentField, int level) {

        // 拼接普通类型数据
        for (FieldMapper fieldMapper : fieldMappers) {
            buildMapperScript(builder, originParentPath, targetParentField, FieldType.OBJECT, level, fieldMapper);
        }

        // 拼接对象
        for (Map.Entry<String, ObjectMapper> mapperEntry : childMapper.entrySet()) {

            builder.appendWithEnter("");

            ObjectMapper objectMapper = mapperEntry.getValue();
            if (!GroovyUtil.isArray(objectMapper.fieldType)) {
                objectMapper.buildScript(builder, originParentPath + "." + mapperEntry.getKey(), targetParentField, level);
                continue;
            }

            // 遍历数组类型时的变量名
            int originArrayVariablesNo = variablesManager.getOriginArrayVariablesNo();
            String itemVariables = "item" + originArrayVariablesNo;

            // 先创建变量
            buildVariables(builder, targetParentField, level, objectMapper.baseArrayFieldMapper);

            // 基础类型数组
            if (GroovyUtil.isBaseTypeArray(objectMapper.fieldType)) {
                // 数组
                builder.appendWithSpaceEnter("for (def "+ itemVariables + " : " + originParentPath + "?." + objectMapper.getFieldName() + ") {", level);
                buildMapperScript(builder, itemVariables, targetParentField, FieldType.OBJECT_ARRAY, level + 1, objectMapper.baseArrayFieldMapper);
                builder.appendWithSpaceEnter("}", level);
                continue;
            }

            // 对象数组
            builder.appendWithSpaceEnter("for (def " + itemVariables + ": " + originParentPath + "?." + objectMapper.getFieldName() + ") {", level);
            objectMapper.buildScript(builder, itemVariables, targetParentField, level + 1);
            builder.appendWithSpaceEnter("}", level);
        }

    }

    private void buildMapperScript(
            GroovyBuilder builder, String originParentPath,
            String targetParentField, String targetParentFieldType,
            int level, FieldMapper fieldMapper) {

        targetParentField = buildVariables(builder, targetParentField, level, fieldMapper);
        fieldMapper.buildScript(builder, originParentPath, targetParentField, targetParentFieldType, level);
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
