package pers.errorcode.result;

import pers.errorcode.code.AppErrorCode;
import pers.errorcode.code.ErrorCode;

import java.io.Serializable;

/**
 * @auther ken.ck
 * @date 2023/3/12 13:17
 * 失败，有错误码和错误信息
 * @param errorCode ErrorCode 错误码
 * @param <T>       范型参数
 * @return Result
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -1525914055479353120L;

    /**
     * 是否成功
     */
    private final Boolean success;
    /**
     * 错误码
     */
    private final String code;
    /**
     * 提示信息
     */
    private final String message;
    /**
     * 返回数据
     */
    private final T data;

    /**
     * 私有构造方法，不允许直接创建对象
     * 也就是不允许传入自定义的错误码，所有错误码的定义必须在枚举中定义
     * @param code    错误码
     * @param message 提示信息
     * @param data    返回的数据
     * @param success 是否成功
     */
    protected Result(Boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }
    /**
     * 创建 Result 对象
     * @param errorCode ErrorCode 错误码
     * @param data      返回的数据
     * @param success   是否成功
     */
    private static <T> Result<T> of(Boolean success, ErrorCode errorCode, T data) {
        return new Result<>(success, errorCode.getCode(), errorCode.getMessage(), data);
    }

    /**
     * 成功，没有返回数据
     * @param <T> 范型参数
     * @return Result
     */
    public static <T> Result<T> success() {
        return of(true, AppErrorCode.SUCCESS, null);
    }

    /**
     * 成功，有返回数据
     * @param data 返回数据
     * @param <T>  范型参数
     * @return Result
     */
    public static <T> Result<T> success(T data) {
        return of(true, AppErrorCode.SUCCESS, data);
    }

    /**
     * 失败，有错误信息
     * @param message 错误信息
     * @param <T>     范型参数
     * @return Result
     */
    public static <T> Result<T> fail(String message) {
        return of(false, AppErrorCode.FAIL.setMessage(message), null);
    }

    /**
     * 失败，有错误码和错误信息
     * @param errorCode ErrorCode 错误码
     * @param <T>       范型参数
     * @return Result
     */
    public static <T> Result<T> fail(ErrorCode errorCode) {
        return of(false, errorCode, null);
    }
        
    /**
     * 获取错误码
     * @return 错误码
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取提示信息
     * @return 提示信息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 获取数据
     * @return 返回的数据
     */
    public T getData() {
        return data;
    }

    /**
     * 获取是否成功
     * @return 是否成功
     */
    public Boolean getSuccess() {
        return success;
    }
    
}
