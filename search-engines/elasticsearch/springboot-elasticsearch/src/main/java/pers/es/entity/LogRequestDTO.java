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
     * 查询日志主题。
     */
    private String topic;
    /**
     * 查询条件
     */
    private String kword;

}
