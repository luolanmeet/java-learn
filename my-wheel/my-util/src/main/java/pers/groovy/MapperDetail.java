package pers.groovy;

import java.util.List;

/**
 * 映射详情
 * @auther ken.ck
 * @date 2021/4/29 22:00
 */
public class MapperDetail {

    private String mapperStr;

    /**
     * 源数据类型
     */
    private String originFieldType;

    /**
     * 源数据值
     */
    private String originVal;

    /**
     * 目标数据类型
     */
    private String targetFieldType;

    /**
     * 目标数据值
     */
    private String targetVal;

    /**
     * 目标数据字段
     */
    private String targetField;

    /**
     * 操作
     */
    private List<Operate> operates;

    /**
     * 是否非空
     */
    private boolean isNotNull;

    /**
     * 上级节点
     */
    private String parent;

    public MapperDetail() {
    }

    public static MapperDetail getSimpleMapperDetail(String mapperStr) {

        MapperDetail mapperDetail = new MapperDetail();
        mapperDetail.setMapperStr(mapperStr);

        String[] sentences = mapperStr.split(GroovyConstant.SENTENCE_SPLIT);

        // 处理字段映射
        String fieldMapper = sentences[0];
        String[] strArray = fieldMapper.split(GroovyConstant.SENTENCE_INNER_SPLIT);
        if (strArray.length != 4) {
            throw new RuntimeException("不符合映射声明语义， " + fieldMapper);
        }
        mapperDetail.setOriginVal(strArray[0]);
        mapperDetail.setOriginFieldType(strArray[1]);

        mapperDetail.setTargetFieldType(strArray[3]);
        mapperDetail.setTargetVal(strArray[2]);

        String[] targetFields = strArray[2].split(GroovyConstant.POINT_SPLIT);
        mapperDetail.setTargetField(targetFields[targetFields.length - 1]);

        String[] fields = strArray[0].split(GroovyConstant.POINT_SPLIT);
        mapperDetail.setParent(fields.length > 1 ? fields[0] : null);

        return mapperDetail;
    }

    public void parseOperate() {
        String[] sentences = mapperStr.split(GroovyConstant.SENTENCE_SPLIT);
        for (int i = 1; i < sentences.length; i++) {
            // TODO 校验操作
            String[] split = sentences[i].split(GroovyConstant.SENTENCE_INNER_SPLIT);
            if (OperateType.NOT_NULL.equals(split[0])) {
                this.isNotNull = true;
                continue;
            }
            operates.add(new Operate(split[0], split.length > 1 ? split[1] : null));
        }
    }

    public void buildScript(
            GroovyBuilder groovyBuilder,
            String parentPath, String parentField, int level) {

        // TODO 区分类型
        // TODO 补充操作

        String originField = parentPath + "?." + originVal;

        if (isNotNull) {
            groovyBuilder.appendWithSpaceEnter("if (!" + originField + ") {", level);
            groovyBuilder.appendWithSpaceEnter("map.put(\"error\", \"" + originField + "为空\")", level + 1);
            groovyBuilder.appendWithSpaceEnter("return map", level + 1);
            groovyBuilder.appendWithSpaceEnter("}", level);
        }

        groovyBuilder.appendWithSpaceEnter(
                parentField + ".put(\"" + targetField + "\", " + parentPath + "?." + originVal + ")", level);
    }

    public String getOriginFieldType() {
        return originFieldType;
    }

    public void setOriginFieldType(String originFieldType) {
        this.originFieldType = originFieldType;
    }

    public String getOriginVal() {
        return originVal;
    }

    public void setOriginVal(String originVal) {
        this.originVal = originVal;
    }

    public String getTargetFieldType() {
        return targetFieldType;
    }

    public void setTargetFieldType(String targetFieldType) {
        this.targetFieldType = targetFieldType;
    }

    public String getTargetVal() {
        return targetVal;
    }

    public void setTargetVal(String targetVal) {
        this.targetVal = targetVal;
    }

    public List<Operate> getOperates() {
        return operates;
    }

    public void setOperates(List<Operate> operates) {
        this.operates = operates;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getMapperStr() {
        return mapperStr;
    }

    public void setMapperStr(String mapperStr) {
        this.mapperStr = mapperStr;
    }

    public String getTargetField() {
        return targetField;
    }

    public void setTargetField(String targetField) {
        this.targetField = targetField;
    }
}
