package pers.errorcode.code;

/**
 * @auther ken.ck
 * @date 2023/3/12 13:01
 *
 * 系统错误码枚举类
 * 应用系统相关的错误定义在这个枚举类中
 * 新增错误码只需要按照编号递增即可
 */
public enum AppErrorCode implements ErrorCode {

    SUCCESS("A0000", "成功"),
    FAIL("A0001", "失败"),
    SYSTEM_ERROR("A0002", "系统异常"),
    ;

    /**
     * 错误码
     */
    private final String code;
    /**
     * 错误信息
     */
    private String message;

    AppErrorCode(String code, String message) {
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
     *
     * @param message 错误信息
     * @return ErrorCode
     */
    @Override
    public ErrorCode setMessage(String message) {
        this.message = message;
        return this;
    }
}