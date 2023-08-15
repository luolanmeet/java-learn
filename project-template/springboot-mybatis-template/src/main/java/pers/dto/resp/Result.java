package pers.dto.resp;

import lombok.Data;
import pers.common.TraceTool;

/**
 * @auther ken.ck
 * @date 2023/6/20 15:30
 */
@Data
public class Result<T> {

    /** 鹰眼Id*/
    private String traceId;

    /** 错误码*/
    private String errorCode;

    /** 错误描述*/
    private String errorMessage;

    /**返回的数据报文*/
    private T data;

    /**是否成功*/
    private boolean success = true;

    /**
     * 便于直接返回成功
     * @return
     */
    public static <T> Result<T> setOfSuccess(){

        Result<T> result = new Result<T>();
        result.setSuccess(true);
        result.setErrorMessage("");
        result.setTraceId(TraceTool.getTraceId());
        return result;
    }

    /**
     * 便于直接返回成功
     * @return
     */
    public static <T> Result<T> setOfSuccess(T t){

        Result<T> result = new Result<T>();
        result.setSuccess(true);
        result.setErrorMessage("");
        result.setTraceId(TraceTool.getTraceId());
        result.setData(t);
        return result;
    }

    /**
     * 便于直接返回未知异常错误
     * @param errDesc
     * @return
     */
    public static <T> Result<T> unKnowErrorOfMsg(String errDesc){

        Result<T> result = new Result<T>();
        result.setErrorMessage(errDesc);
        result.setTraceId(TraceTool.getTraceId());
        result.setSuccess(false);
        return result;
    }

}
