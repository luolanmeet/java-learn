package pers.protocol;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class InvokerProtocol implements Serializable {

    private static final long serialVersionUID = -4732542437627787166L;

    // 类名
    private String className;

    // 方法名
    private String methodName;

    // 形参列表
    private Class<?>[] parames;

    // 方法参数
    private Object[] valuse;

}
