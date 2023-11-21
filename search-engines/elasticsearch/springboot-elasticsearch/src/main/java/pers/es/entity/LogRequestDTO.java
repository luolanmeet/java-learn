package pers.es.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 日志请求<br>
 */
@Getter
@Setter
public class LogRequestDTO extends BaseDTO {

    /**
     * 项目名称
     */
    private String project;
    /**
     * 日志库名称
     */
    private String logStore;
    /**
     * 开始时间-精度为秒
     */
    private Date from;
    /**
     * 结束时间-精度为秒
     */
    private Date to;

    /**
     * 开始时间-精度为秒
     */
    private String fromDate;
    /**
     * 结束时间-精度为秒
     */
    private String toDate;

    /**
     * 查询日志主题。
     */
    private String topic;
    /**
     * 查询条件
     */
    private String kword;
    /**
     * 请求返回日志的起始点。取值范围为 0 或正整数，默认值为 0
     */
    private Integer offset;
    /**
     * 请求返回的最大日志条数。取值范围为 0~100，默认值为 100
     */
    private Integer line;
    /**
     * 是否按日志时间戳逆序返回日志 true 表示逆序，false 表示顺序，默认值为 false。
     */
    private boolean reverse;

}
