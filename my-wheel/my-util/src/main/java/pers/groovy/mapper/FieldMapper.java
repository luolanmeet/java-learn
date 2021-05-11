package pers.groovy.mapper;

import pers.groovy.constant.FieldType;
import pers.groovy.constant.GroovyConstant;
import pers.groovy.constant.OperateType;
import pers.groovy.util.GroovyBuilder;
import pers.groovy.util.GroovyUtil;
import pers.groovy.util.VariablesManager;

import java.util.ArrayList;
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
     * 源数据字段路径（相对于上级节点）
     * user.info.name
     */
    private String originFieldPath;

    /**
     * 祖先字段字段名
     * user
     */
    private String originFieldParentName;

    /**
     * 源数据字段类型
     * string
     */
    private String originFieldType;

    /**
     * 源数据字段路径（完整的路径）
     * result.user.info.name
     */
    private String completeOriginFieldPath;

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
    private List<Operate> operates = new ArrayList<>();

    /**
     * 是否做非空校验
     */
    private boolean isNotNull;

    /**
     * 是否自动做类型转换
     */
    private boolean isAutoChangeType = true;

    /**
     * 为空时，设置默认值的操作
     */
    private Operate defaultValOperate;

    public static FieldMapper getSimpleMapper(
            String parentCompleteOriginFieldPath,
            String mapperStr, VariablesManager variablesManager) {

        FieldMapper fieldMapper = new FieldMapper();
        fieldMapper.setMapperStr(mapperStr);

        // 切分声明
        String[] sentences = mapperStr.split(GroovyConstant.SENTENCE_SPLIT);

        // 处理字段映射
        String fieldMapperStr = sentences[0];
        String[] strArray = fieldMapperStr.split(GroovyConstant.SENTENCE_INNER_SPLIT);
        if (strArray.length != 4) {
            throw new RuntimeException("不符合映射声明语义， " + fieldMapperStr);
        }

        fieldMapper.setCompleteOriginFieldPath(parentCompleteOriginFieldPath.isEmpty() ? strArray[0] : parentCompleteOriginFieldPath + "." + strArray[0]);
        fieldMapper.setOriginFieldPath(strArray[0]);
        fieldMapper.setOriginFieldType(strArray[1]);

        fieldMapper.setTargetFieldPath(strArray[2]);
        fieldMapper.setTargetFieldType(strArray[3]);

        String[] targetFields = strArray[2].split(GroovyConstant.POINT_SPLIT);
        fieldMapper.setTargetFieldName(targetFields[targetFields.length - 1]);

        String[] fields = strArray[0].split(GroovyConstant.POINT_SPLIT);
        fieldMapper.setOriginFieldParentName(fields.length > 1 ? fields[0] : null);

        // 解析操作
        fieldMapper.parseOperate(mapperStr, variablesManager);

        return fieldMapper;
    }

    private void parseOperate(String mapperStr, VariablesManager variablesManager) {

        String[] sentences = mapperStr.split(GroovyConstant.SENTENCE_SPLIT);
        for (int i = 1; i < sentences.length; i++) {

            String[] operateSentence = sentences[i].split(GroovyConstant.SENTENCE_INNER_SPLIT, 2);

            // 校验操作声明
            GroovyUtil.checkOperate(operateSentence);

            // 声明了类型转换 则不进行自动的类型转换
            if (OperateType.CHANGE_TYPE.equals(operateSentence[0]) || OperateType.DATA_FORMAT.equals(operateSentence[0])) {
                this.isAutoChangeType = false;
            }

            // 非空检验
            if (OperateType.NOT_NULL.equals(operateSentence[0])) {
                this.isNotNull = true;
                this.defaultValOperate = null;
                continue;
            }

            if (OperateType.DEFAULT_VALUE.equals(operateSentence[0])) {

                // 非空判断 和 空设默认值 是互斥的
                if (this.isNotNull) {
                    continue;
                }

                this.defaultValOperate = new Operate(operateSentence[0], operateSentence[1]);
                continue;
            }

            if (OperateType.DATA_FORMAT.equals(operateSentence[0])) {
                variablesManager.registerDateFormatVariables(operateSentence[1]);
            }

            operates.add(new Operate(operateSentence[0], operateSentence.length > 1 ? operateSentence[1] : null));
        }
    }

    /**
     * 生成脚本
     * @param groovyBuilder
     * @param variablesManager
     * @param originParentPath
     * @param targetParentField
     * @param targetParentFieldType
     * @param level
     */
    public void generateScript(
            GroovyBuilder groovyBuilder, VariablesManager variablesManager, String originParentPath,
            String targetParentField, String targetParentFieldType, int level) {

        String originField = originFieldPath.isEmpty() ? originParentPath : originParentPath  + "?." + originFieldPath;

        if (isNotNull) {
            GroovyUtil.appendNotNull(groovyBuilder, originField, level);
        }

        // 是否有声明为空时设值的操作 -- 添加开始的判断
        if (defaultValOperate != null) {
            groovyBuilder.appendWithSpaceEnter("if (" + originField + ") {", level);
            level++;
        }

        // 进行默认的类型转换
        if (isAutoChangeType) {
            originField = GroovyUtil.changeType(originFieldType, targetFieldType, originField);
        }

        // 执行操作
        originField = GroovyUtil.executeOperate(variablesManager, operates, originFieldType, originField);

        // 对象或数组 需要不同处理
        if (FieldType.OBJECT.equals(targetParentFieldType)) {
            groovyBuilder.appendWithSpaceEnter(
                    targetParentField + ".put(\"" + targetFieldName + "\", " + originField + ")", level);
        } else {
            groovyBuilder.appendWithSpaceEnter(
                    targetParentField + ".add(" + originField + ")", level);
        }

        // 是否有声明为空时设值的操作 -- 添加结束的设置默认值
        if (defaultValOperate != null) {

            // 处理默认值
            String defaultValue = GroovyUtil.getDefaultValue(targetFieldType, defaultValOperate.getOperateVal());

            level--;
            groovyBuilder.appendWithSpaceEnter("} else {", level);
            if (FieldType.OBJECT.equals(targetParentFieldType)) {
                groovyBuilder.appendWithSpaceEnter(
                        targetParentField + ".put(\"" + targetFieldName + "\", " + defaultValue + ")", level + 1);
            } else {
                groovyBuilder.appendWithSpaceEnter(
                        targetParentField + ".add(" + defaultValue + ")", level + 1);
            }
            groovyBuilder.appendWithSpaceEnter("}", level);
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

    public boolean isNotNull() {
        return isNotNull;
    }

    public void setNotNull(boolean notNull) {
        isNotNull = notNull;
    }

    public boolean isAutoChangeType() {
        return isAutoChangeType;
    }

    public void setAutoChangeType(boolean autoChangeType) {
        isAutoChangeType = autoChangeType;
    }

    public Operate getDefaultValOperate() {
        return defaultValOperate;
    }

    public void setDefaultValOperate(Operate defaultValOperate) {
        this.defaultValOperate = defaultValOperate;
    }

    public String getCompleteOriginFieldPath() {
        return completeOriginFieldPath;
    }

    public void setCompleteOriginFieldPath(String completeOriginFieldPath) {
        this.completeOriginFieldPath = completeOriginFieldPath;
    }
}
