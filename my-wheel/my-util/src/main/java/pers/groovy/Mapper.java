package pers.groovy;

import java.util.*;

/**
 * 原字段路径:字段类型:目标字段路径:字段类型;操作符:[操作值];操作符:[操作值];操作符:[操作值];
 * @auther ken.ck
 * @date 2021/4/29 20:13
 */
public class Mapper {

    /**
     * 当前表示的原数据字段
     */
    private String field;

    /**
     * 当前表示的原数据字段
     */
    private String type;

    /**
     * 字段映射
     */
    private List<MapperDetail> mapperDetails;

    /**
     * 基础数组型字段映射
     */
    private MapperDetail baseArrayMapperDetails;

    /**
     * 子属性映射
     * 源数据是 对象或数组 会有这类
     */
    private Map<String, Mapper> childMapper;

    private VariablesManager variablesManager;

    public Mapper(String field, String type, VariablesManager variablesManager) {
        this.field = field;
        this.type = type;
        this.variablesManager = variablesManager;

        this.childMapper = new HashMap<>();
        this.mapperDetails = new ArrayList<>();
    }

    public void addChild(String mapperStr) {

        MapperDetail mapperDetail = MapperDetail.getSimpleMapperDetail(mapperStr);
        String parent = mapperDetail.getParent();

        // 注册需要声明的变量
        variablesManager.registerVariablesType(mapperDetail.getTargetVal(), mapperDetail.getTargetFieldType());

        // 有父节点，表示未到最小解析字段
        if (parent != null) {

            Mapper mapper = childMapper.get(parent);
            if (mapper == null) {
                // 默认父节点是对象类型
                mapper = new Mapper(parent, FieldType.OBJECT, variablesManager);
                childMapper.put(parent, mapper);
            }
            mapper.addChild(mapperStr.split(GroovyConstant.POINT_SPLIT, 2)[1]);
            return ;
        }

        // 无父节点

        // 【是最小映射单元】
        if (!FieldType.OBJECT.equals(mapperDetail.getOriginFieldType())
                && !GroovyUtil.isArray(mapperDetail.getOriginFieldType())) {
            mapperDetail.parseOperate();
            mapperDetails.add(mapperDetail);
            return ;
        }

        // 【是对象与数组的声明】，添加映射工具类
        Mapper mapper = childMapper.get(mapperDetail.getOriginVal());
        if (mapper == null) {
            mapper = new Mapper(mapperDetail.getOriginVal(), mapperDetail.getOriginFieldType(), variablesManager);
            childMapper.put(mapperDetail.getOriginVal(), mapper);
        } else {
            // 更新类型
            mapper.setType(mapperDetail.getOriginFieldType());
        }

        // 如果是基本数据类型的数组，则也设置映射关系
        if (GroovyUtil.isBaseTypeArray(mapperDetail.getOriginFieldType())) {
            mapperDetail.setOriginVal("");
            mapper.baseArrayMapperDetails = mapperDetail;
        }

    }

    public void buildScript(GroovyBuilder builder, String originParentPath, String targetParentField, int level) {

        // 拼接普通类型数据
        for (MapperDetail mapperDetail : mapperDetails) {
            buildMapperScript(builder, originParentPath, targetParentField, FieldType.OBJECT, level, mapperDetail);
        }

        builder.appendEnter();

        // 拼接对象
        for (Map.Entry<String, Mapper> mapperEntry : childMapper.entrySet()) {

            builder.appendEnter();

            Mapper mapper = mapperEntry.getValue();
            if (!GroovyUtil.isArray(mapper.type)) {
                mapper.buildScript(builder, originParentPath + "." + mapperEntry.getKey(), targetParentField, level);
                continue;
            }

            // 基础类型数组
            if (GroovyUtil.isBaseTypeArray(mapper.type)) {

                // 先创建变量
                buildVariables(builder, targetParentField, level, mapper.baseArrayMapperDetails);

                int originArrayVariablesNo = variablesManager.getOriginArrayVariablesNo();
                String itemVariables = "item" + originArrayVariablesNo;

                // 数组
                builder.appendWithSpaceEnter("for (def "+ itemVariables + " : " + originParentPath + "?." + mapper.getField() + ") {", level);
                buildMapperScript(builder, itemVariables, targetParentField, FieldType.OBJECT_ARRAY, level + 1, mapper.baseArrayMapperDetails);
                builder.appendWithSpaceEnter("}", level);
                continue;
            }

            // 对象数组
            builder.appendWithSpaceEnter("for (def item : " + originParentPath + "?." + mapper.getField() + ") {", level);
            mapper.buildScript(builder, "item", targetParentField, level + 1);
            builder.appendWithSpaceEnter("}", level);
        }

        builder.appendEnter();
    }

    private void buildMapperScript(
            GroovyBuilder builder, String originParentPath,
            String targetParentField, String targetParentFieldType,
            int level, MapperDetail mapperDetail) {

        targetParentField = buildVariables(builder, targetParentField, level, mapperDetail);
        mapperDetail.buildScript(builder, originParentPath, targetParentField, targetParentFieldType, level);
    }

    private String buildVariables(GroovyBuilder builder, String targetParentField, int level, MapperDetail mapperDetail) {
        String targetVal = mapperDetail.getTargetVal();
        String[] fields = targetVal.split(GroovyConstant.POINT_SPLIT);

        StringJoiner tmpFieldPath = new StringJoiner(".");
        // 创建目标变量
        for (String field : fields) {
            tmpFieldPath.add(field);
            targetParentField = variablesManager.registerVariables(
                    builder, tmpFieldPath.toString(), targetParentField, field, level);
        }
        return targetParentField;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
