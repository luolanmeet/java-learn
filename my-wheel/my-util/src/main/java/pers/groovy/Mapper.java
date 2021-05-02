package pers.groovy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        // 有父节点，表示未到最小解析字段
        if (parent != null) {

            Mapper mapper = childMapper.get(parent);
            if (mapper == null) {
                // 默认父节点是对象类型
                mapper = new Mapper(parent, GroovyConstant.OBJECT, variablesManager);
                childMapper.put(parent, mapper);
            }
            mapper.addChild(mapperStr.split(GroovyConstant.POINT_SPLIT, 2)[1]);
            return ;
        }

        // 无父节点

        // 【是最小映射单元】
        if (!GroovyConstant.OBJECT.equals(mapperDetail.getOriginFieldType())
                && !GroovyConstant.ARRAY.equals(mapperDetail.getOriginFieldType())) {
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
    }

    public void buildScript(GroovyBuilder builder, String parentPath, String parentField, int level) {

        // 拼接普通类型数据
        for (MapperDetail mapperDetail : mapperDetails) {

            // 构造目标 变量
            String targetVal = mapperDetail.getTargetVal();
            String[] fields = targetVal.split(GroovyConstant.POINT_SPLIT);
            if (fields.length > 1) {
                String tmpParentField = parentField;
                for (int i = 0; i < fields.length - 1; i++) {
                    tmpParentField = variablesManager.registerVariables(
                            builder, fields[i], i, tmpParentField, GroovyConstant.OBJECT, level);
                }
                builder.appendWithSpaceEnter("");
                mapperDetail.buildScript(builder, parentPath, tmpParentField, level);
            } else {
                mapperDetail.buildScript(builder, parentPath, parentField, level);
            }

            builder.appendWithSpaceEnter("");
        }

        // 拼接对象
        for (Map.Entry<String, Mapper> mapperEntry : childMapper.entrySet()) {

            Mapper mapper = mapperEntry.getValue();
            if (!GroovyConstant.ARRAY.equals(mapper.type)) {
                mapper.buildScript(builder, parentPath + "." + mapperEntry.getKey(), parentField, level);
                continue;
            }

            // 数组
            builder.appendWithSpaceEnter("for (def item : " + parentPath + "?." + field + ") {", level);
            mapper.buildScript(builder, "item", parentField, level + 1);
            builder.appendWithSpaceEnter("}", level);
        }

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
