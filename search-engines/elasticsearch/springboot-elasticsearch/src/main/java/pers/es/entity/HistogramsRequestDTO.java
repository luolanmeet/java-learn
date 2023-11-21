package pers.es.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 日志数量请求参数封装
 */
@Getter
@Setter
public class HistogramsRequestDTO extends BaseDTO {

    /**
     * 项目名称
     */
    private String project;
    /**
     * 日志库名称
     */
    private String logStore;
    /**
     * 查询日志主题
     */
    private String topic;
    /**
     * 查询条件
     */
    private String word;
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

}
