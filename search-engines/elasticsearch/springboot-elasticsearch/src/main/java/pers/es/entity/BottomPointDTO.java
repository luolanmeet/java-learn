package pers.es.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.Map;

/**
 * @auther ken.ck
 * @date 2023/11/22 15:36
 */
@Data
@Setting(shards = 1, replicas = 3)
@Document(indexName = "bottom_log_index", versionType = Document.VersionType.EXTERNAL)
public class BottomPointDTO {

    /**
     * 唯一ID
     */
    @Field(type = FieldType.Keyword)
    private String uniqueId;

    /**
     * 鹰眼id-必填
     */
    @Field(type = FieldType.Keyword)
    private String traceId;

    /**
     * 鹰眼rpc_id
     */
    @Field(type = FieldType.Keyword)
    private String rpcId;

    /**
     * 调用方IP-必填
     */
    @Field(type = FieldType.Keyword)
    private String clientIp;

    /**
     * 本机IP(采集IP)-必填
     */
    @Field(type = FieldType.Keyword)
    private String localIp;

    /**
     * 时间-必填
     */
    @Field(type = FieldType.Keyword)
    private String startTime;

    /**
     * 执行耗时
     */
    @Field(type = FieldType.Long)
    private Long cost;

    /**
     * 接口中文名称
     */
    @Field(type = FieldType.Keyword)
    private String interfaceName;

    /**
     * 执行具体方法
     */
    @Field(type = FieldType.Keyword)
    private String methodName;

    /**
     * 执行的具体类(非全路径类名)
     */
    @Field(type = FieldType.Keyword)
    private String clazzName;

    /**
     * 执行的具体类(全路径类名)
     */
    @Field(type = FieldType.Keyword)
    private String packClass;

    /**
     * 是否成功(指业务级别:Result返回的)
     * -1 失败  0 未设置   1成功
     */
    @Field(type = FieldType.Integer)
    private Integer success;

    /**
     * 错误码
     */
    @Field(type = FieldType.Keyword)
    private String errorCode;

    /**
     * 错误信息
     */
    @Field(type = FieldType.Keyword)
    private String errorMessage;

    /**
     * 真实执行参数(请求/方法参数)
     */
    @Field(type = FieldType.Object)
    private Object args;

    /**
     * 其他执行参数(用于打印真实的请求参数，如果需要)
     */
    @Field(type = FieldType.Object)
    private Object realArgs;

    /**
     * 返回参数(结果参数)Json
     */
    @Field(type = FieldType.Object)
    private Object result;

    /**
     * 调用方名称-必填（应用名称-编码）
     */
    @Field(type = FieldType.Keyword)
    private String clientApp;

    /**
     * 是否发生异常
     */
    @Field(type = FieldType.Boolean)
    private Boolean stack;

    /**
     * 堆栈信息(如发生异常必须打印该字段)
     */
    @Field(type = FieldType.Keyword)
    private String stackMessage;

    /**
     * 日志打印的类型   1 入口的日志，2 出口日志。 3 内部链路日志
     */
    @Field(type = FieldType.Integer)
    private Integer logType;

    /**
     * 扩展显示
     */
    @Field(type = FieldType.Object)
    private Map<String,String> extendMap;

}
