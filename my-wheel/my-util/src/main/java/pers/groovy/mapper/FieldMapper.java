package pers.groovy.mapper;

import pers.groovy.constant.FieldType;
import pers.groovy.constant.GroovyConstant;
import pers.groovy.constant.OperateType;
import pers.groovy.util.GroovyBuilder;
import pers.groovy.util.GroovyUtil;

import java.util.List;

/**
 * 基本类型 字段映射类
 * @auther ken.ck
 * @date 2021/4/29 22:00
 */
public class FieldMapper {

    /**
     * 原始声明字符串
     * user.info.name:string:baseInfo.name:string;notNull;
     */
    private String mapperStr;

    /**
     * 源数据字段路径
     * user.info.name
     */
    private String originFieldPath;

    /**
     * 源数据字段上级字段名
     * user
     */
    private String originFieldParentName;

    /**
     * 源数据字段类型
     * string
     */
    private String originFieldType;

    /**
     * 目标数据字段路径
     * baseInfo.name
     */
    private String targetFieldPath;

    /**
     * 目标数据字段名
     * name
     */
    private String targetFieldName;

    /**
     * 目标数据类型
     * string
     */
    private String targetFieldType;

    /**
     * 操作
     * notNull、changeType
     */
    private List<Operate> operates;

    /**
     * 是否做非空校验
     */
    private boolean isNotNull;

    public static FieldMapper getSimpleMapper(String mapperStr) {

        FieldMapper mapperDetail = new FieldMapper();
        mapperDetail.setMapperStr(mapperStr);

        String[] sentences = mapperStr.split(GroovyConstant.SENTENCE_SPLIT);

        // 处理字段映射
        String fieldMapper = sentences[0];
        String[] strArray = fieldMapper.split(GroovyConstant.SENTENCE_INNER_SPLIT);
        if (strArray.length != 4) {
            throw new RuntimeException("不符合映射声明语义， " + fieldMapper);
        }
        mapperDetail.setOriginFieldPath(strArray[0]);
        mapperDetail.setOriginFieldType(strArray[1]);

        mapperDetail.setTargetFieldType(strArray[3]);
        mapperDetail.setTargetFieldPath(strArray[2]);

        String[] targetFields = strArray[2].split(GroovyConstant.POINT_SPLIT);
        mapperDetail.setTargetFieldName(targetFields[targetFields.length - 1]);

        String[] fields = strArray[0].split(GroovyConstant.POINT_SPLIT);
        mapperDetail.setOriginFieldParentName(fields.length > 1 ? fields[0] : null);

        return mapperDetail;
    }

    public void parseOperate() {
        String[] sentences = mapperStr.split(GroovyConstant.SENTENCE_SPLIT);
        for (int i = 1; i < sentences.length; i++) {
            // TODO 校验操作 入参是否正确
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
            String originParentPath, String targetParentField, String targetParentFieldType, int level) {

        // TODO 区分类型
        // TODO 补充操作

        String originField = originFieldPath.isEmpty() ? originParentPath : originParentPath  + "?." + originFieldPath;

        if (isNotNull) {
            groovyBuilder.appendWithSpaceEnter("if (!" + originField + ") {", level);
            groovyBuilder.appendWithSpaceEnter("map.put(\"error\", \"" + originField + "为空\")", level + 1);
            groovyBuilder.appendWithSpaceEnter("return map", level + 1);
            groovyBuilder.appendWithSpaceEnter("}", level);
        }

        // 类型转换 操作补充
        originField = GroovyUtil.caseType(originFieldType, targetFieldType, originField);

        // 对象或数组 需要不同处理
        if (FieldType.OBJECT.equals(targetParentFieldType)) {
            groovyBuilder.appendWithSpaceEnter(
                    targetParentField + ".put(\"" + targetFieldName + "\", " + originField + ")", level);
        } else {
            groovyBuilder.appendWithSpaceEnter(
                    targetParentField + ".add(" + originField + ")", level);
        }
    }

    public String getOriginFieldType() {
        return originFieldType;
    }

    public void setOriginFieldType(String originFieldType) {
        this.originFieldType = originFieldType;
    }

    public String getOriginFieldPath() {
        return originFieldPath;
    }

    public void setOriginFieldPath(String originFieldPath) {
        this.originFieldPath = originFieldPath;
    }

    public String getTargetFieldType() {
        return targetFieldType;
    }

    public void setTargetFieldType(String targetFieldType) {
        this.targetFieldType = targetFieldType;
    }

    public String getTargetFieldPath() {
        return targetFieldPath;
    }

    public void setTargetFieldPath(String targetFieldPath) {
        this.targetFieldPath = targetFieldPath;
    }

    public List<Operate> getOperates() {
        return operates;
    }

    public void setOperates(List<Operate> operates) {
        this.operates = operates;
    }

    public String getOriginFieldParentName() {
        return originFieldParentName;
    }

    public void setOriginFieldParentName(String originFieldParentName) {
        this.originFieldParentName = originFieldParentName;
    }

    public String getMapperStr() {
        return mapperStr;
    }

    public void setMapperStr(String mapperStr) {
        this.mapperStr = mapperStr;
    }

    public String getTargetFieldName() {
        return targetFieldName;
    }

    public void setTargetFieldName(String targetFieldName) {
        this.targetFieldName = targetFieldName;
    }
}
