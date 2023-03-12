package pers.errorcode.exception;

import pers.errorcode.code.BizErrorCode;
import pers.errorcode.code.ErrorCode;

/**
 * @auther ken.ck
 * @date 2023/3/12 13:13
 * 业务异常类
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1124406084483723007L;

    /**
     * 错误码接口
     */
    private final ErrorCode errorCode;

    /**
     * 构造一个没有错误信息的 <code>BizException</code>
     */
    public BizException() {
        super();
        this.errorCode = BizErrorCode.BIZ_ERROR;
    }

    /**
     * 使用错误信息 message 构造 BizException
     * @param message 错误信息
     */
    public BizException(String message) {
        super(message);
        this.errorCode = BizErrorCode.BIZ_ERROR.setMessage(message);
    }

    /**
     * 使用指定的 Throwable 和 Throwable.toString() 作为异常信息来构造 BizException
     * @param cause 错误原因， 通过 Throwable.getCause() 方法可以获取传入的 cause信息
     */
    public BizException(Throwable cause) {
        super(cause);
        this.errorCode = BizErrorCode.BIZ_ERROR;
    }

    /**
     * 使用错误信息和 Throwable 构造 BizException
     * @param message 错误信息
     * @param cause   错误原因
     */
    public BizException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = BizErrorCode.BIZ_ERROR.setMessage(message);
    }

    /**
     * @param errorCode ErrorCode
     */
    public BizException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    /**
     * @param errorCode ErrorCode
     * @param cause     错误原因
     */
    public BizException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    /**
     * 获取错误码
     * @return 错误码
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}