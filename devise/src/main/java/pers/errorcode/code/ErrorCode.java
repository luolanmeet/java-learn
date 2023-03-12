package pers.errorcode.code;

/**
 * 错误码接口类
 * 所有的错误码枚举类需要继承该类，用于错误码分类
 *
 * @auther ken.ck
 * @date 2023/3/12 13:00
 */
public interface ErrorCode {

    /**
     * 获取错误码
     * @return 错误码
     */
    String getCode();

    /**
     * 获取错误信息
     * @return 错误信息
     */
    String getMessage();

    /**
     * 设置错误信息
     * 允许重新设置错误信息，不允许重新设置错误码，所有错误码必须在枚举类中定义，方便统一管理
     * @param message 错误信息
     * @return ErrorCode
     */
    ErrorCode setMessage(String message);

}
