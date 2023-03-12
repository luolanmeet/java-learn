package pers.errorcode.code;

/**
 * @auther ken.ck
 * @date 2023/3/12 13:09
 * 客户端相关的其他错误码
 * 调用第三方服务相关的错误码定义在这个枚举类中
 * 新增错误码只需要按照编号递增即可
 */
public enum ClientErrorCode implements ErrorCode {

    RPC_ERROR("C0001", "rpc调用异常"),
    DATABASE_ERROR("C0002", "数据库连接异常"),
    ;

    /**
     * 错误码
     */
    private final String code;
    /**
     * 错误信息
     */
    private String message;

    ClientErrorCode(String code, String message) {
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