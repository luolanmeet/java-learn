package pers.errorcode.code;

/**
 * @auther ken.ck
 * @date 2023/3/12 13:08
 * 业务错误码枚举类
 * 业务相关的错误定义在这个枚举类中
 * 比如：参数校验相关的错误码，权限相关的错误码
 * 新增错误码只需要按照编号递增即可
 */
public enum BizErrorCode implements ErrorCode {

    BIZ_ERROR("B0001", "业务异常"),
    NO_PERMISSION("B0002", "没有权限"),
    ;

    /**
     * 错误码
     */
    private final String code;
    /**
     * 错误信息
     */
    private String message;

    BizErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取错误码
     * @return 错误码
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * 获取错误信息
     * @return 错误信息
     */
    @Override
    public String getMessage() {
        return this.message;
    }

    /**
     * 设置错误信息
     * 允许重新设置错误信息，不允许重新设置错误码，所有错误码必须在枚举类中定义，方便统一管理
     * @param message 错误信息
     * @return ErrorCode
     */
    @Override
    public ErrorCode setMessage(String message) {
        this.message = message;
        return this;
    }
}
