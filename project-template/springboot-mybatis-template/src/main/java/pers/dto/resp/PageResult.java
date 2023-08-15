package pers.dto.resp;

import lombok.Data;
import pers.common.TraceTool;

import java.util.List;

/**
 * @auther ken.ck
 * @date 2023/6/20 15:30
 */
@Data
public class PageResult<T> {

    private String traceId;

    private long pageSize;

    private long total;

    private List<T> data;

    public PageResult() {
        traceId = TraceTool.getTraceId();
    }
}
